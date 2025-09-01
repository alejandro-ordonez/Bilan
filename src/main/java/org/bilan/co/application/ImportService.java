package org.bilan.co.application;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.application.files.IFileManager;
import org.bilan.co.application.user.IUserImportService;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.common.PagedResponse;
import org.bilan.co.domain.dtos.user.AuthenticatedUserDto;
import org.bilan.co.domain.dtos.user.ImportRequestDto;
import org.bilan.co.domain.dtos.user.enums.ImportStatus;
import org.bilan.co.domain.dtos.user.enums.ImportType;
import org.bilan.co.domain.entities.Colleges;
import org.bilan.co.domain.entities.ImportRequests;
import org.bilan.co.domain.entities.UserInfo;
import org.bilan.co.domain.enums.BucketName;
import org.bilan.co.domain.utils.Tuple;
import org.bilan.co.infraestructure.persistance.CollegesRepository;
import org.bilan.co.infraestructure.persistance.ImportRequestRepository;
import org.bilan.co.infraestructure.persistance.TeachersRepository;
import org.bilan.co.infraestructure.persistance.UserInfoRepository;
import org.bilan.co.utils.Constants;
import org.bilan.co.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ImportService implements IUserImportService {

    @Autowired
    private IFileManager fileManager;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private ImportRequestRepository importRequestRepository;

    @Autowired
    private JwtTokenUtil jwt;

    @Autowired
    private CollegesRepository collegesRepository;

    @Autowired
    private TeachersRepository teachersRepository;

    @Override
    @Async
    public ResponseDto<ImportRequestDto> importUsers(MultipartFile file, ImportType importType, String campusCodeDane, String token) {
        var extension = org.bilan.co.utils.FileUtils.getExtension(file);

        if(!extension.equals(Constants.CSV))
            return new ResponseDto<>("File type not valid", 400, new ImportRequestDto(ImportStatus.Rejected));

        Optional<Colleges> collegeQuery;

        if (importType == ImportType.TeacherImport || importType == ImportType.CollegesImport)
            collegeQuery = Optional.empty();

        else if (campusCodeDane == null) {
            AuthenticatedUserDto authenticatedUserDto = jwt.getInfoFromToken(token);
            String codDaneSede = teachersRepository.getCodDaneSede(authenticatedUserDto.getDocument());
            collegeQuery = collegesRepository.findByCodDaneSede(codDaneSede);

            if (collegeQuery.isEmpty()) {
                String message = "The directive teacher does not have a college linked";
                log.error(message);
                return new ResponseDto<>(message, 400, new ImportRequestDto(ImportStatus.Rejected));
            }
        } else {
            collegeQuery = collegesRepository.findByCodDaneSede(campusCodeDane);
        }

        // Can be empty only if you are not importing a list of teachers
        if (collegeQuery.isEmpty() && importType != ImportType.TeacherImport && importType != ImportType.CollegesImport) {
            String message = "The college couldn't be determined";
            log.error(message);
            return new ResponseDto<>(message, 400, new ImportRequestDto(ImportStatus.Rejected));
        }

        // Upload file to be verified in the next stage using jobs
        var importRequest = new ImportRequests();

        String requestorDocument = jwt.getUsernameFromToken(token);
        UserInfo requestor = new UserInfo();
        requestor.setDocument(requestorDocument);

        importRequest.setRequestor(requestor);
        importRequest.setType(importType);
        importRequest.setStatus(ImportStatus.ReadyForVerification);

        if (collegeQuery.isEmpty())
            importRequest.setCollegeId(null);
        else
            importRequest.setCollegeId(collegeQuery.get().getId());

        importRequestRepository.save(importRequest);

        var bucket = switch (importType) {
            case TeacherImport -> BucketName.BILAN_TEACHER;
            case StudentImport -> BucketName.BILAN_STUDENT_IMPORT;
            case TeacherEnrollment -> BucketName.BILAN_TEACHER_ENROLLMENT;
            case CollegesImport -> BucketName.BILAN_COLLEGE_IMPORT;
        };

        try {
            var uploadResult = fileManager.stageImportFile(file.getInputStream(), bucket, importRequest.getImportId(), extension);
            if (uploadResult) {
                return new ResponseDto<>(Constants.Ok, 200, importRequest.toDto());
            } else{
                importRequest.setStatus(ImportStatus.Rejected);
                importRequestRepository.save(importRequest);
                return new ResponseDto<>("Failed to read the file", 400, new ImportRequestDto(ImportStatus.Rejected));
            }

        } catch (IOException e) {
            importRequest.setStatus(ImportStatus.Rejected);
            importRequestRepository.save(importRequest);
            return new ResponseDto<>("Failed to read the file", 400, new ImportRequestDto(ImportStatus.Rejected));
        }
    }

    @Override
    public ResponseDto<PagedResponse<ImportRequestDto>> getUserRequests(String token, int pageNumber) {
        AuthenticatedUserDto user = jwt.getInfoFromToken(token);
        Page<ImportRequests> query = importRequestRepository.getRequests(
                PageRequest.of(pageNumber, 10), user.getDocument()
        );

        List<ImportRequestDto> requests = query.stream()
                .map(ImportRequests::toDto)
                .toList();

        PagedResponse<ImportRequestDto> pagedResponse = PagedResponse.<ImportRequestDto>builder()
                .nPages(query.getTotalPages())
                .data(requests)
                .build();

        return ResponseDto.<PagedResponse<ImportRequestDto>>builder()
                .code(200)
                .result(pagedResponse)
                .build();
    }

    @Override
    public Optional<Tuple<byte[], String>> downloadRejectUsers(String importId) {
        Optional<ImportRequests> request = importRequestRepository.findById(importId);

        if (request.isEmpty())
            return Optional.empty();

        var bucket = switch (request.get().getType()) {
            case StudentImport -> BucketName.BILAN_STUDENT_IMPORT;
            case TeacherEnrollment -> BucketName.BILAN_TEACHER_ENROLLMENT;
            case TeacherImport -> BucketName.BILAN_TEACHER;
            case CollegesImport -> BucketName.BILAN_COLLEGE_IMPORT;
            default -> throw new IllegalArgumentException();
        };

        return request.map(r -> Tuple.of(fileManager.downloadRejected(r.getImportId(),
                bucket), "text/csv"));
    }
}
