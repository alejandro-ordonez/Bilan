package org.bilan.co.application.evidence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.bilan.co.application.files.IFileManager;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.teacher.EvaluationDto;
import org.bilan.co.domain.dtos.user.AuthenticatedUserDto;
import org.bilan.co.domain.entities.Evidences;
import org.bilan.co.domain.entities.Teachers;
import org.bilan.co.domain.enums.Phase;
import org.bilan.co.domain.projections.IEvidence;
import org.bilan.co.domain.utils.Tuple;
import org.bilan.co.infraestructure.persistance.EvaluationRepository;
import org.bilan.co.infraestructure.persistance.TeachersRepository;
import org.bilan.co.infraestructure.persistance.evidence.EvidenceRepository;
import org.bilan.co.utils.Constants;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Slf4j
@Component
public class EvidenceService implements IEvidenceService {

    private final IFileManager fileStore;
    private final EvidenceRepository evidenceRepository;
    private final EvaluationRepository evaluationRepository;
    private final TeachersRepository teachersRepository;

    public EvidenceService(IFileManager fileStore, EvidenceRepository evidenceRepository,
                           EvaluationRepository evaluationRepository, TeachersRepository teachersRepository) {
        this.fileStore = fileStore;
        this.evidenceRepository = evidenceRepository;
        this.evaluationRepository = evaluationRepository;
        this.teachersRepository = teachersRepository;
    }

    @Override
    @CacheEvict(value = Constants.EVIDENCE_CHECK, key = "#phase + '_' + #tribeId + '_' + #user.document")
    public ResponseDto<String> upload(Phase phase, Long tribeId,
                                      MultipartFile file, AuthenticatedUserDto user) {
        UUID newFileId = UUID.randomUUID();
        String extension = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().indexOf('.'));
        String fileName = String.format("%s%s", newFileId,extension);

        try {
            String path = fileStore.uploadEvidence(fileName, file.getInputStream());
            log.info("File stored: {}", path);

            Evidences evidence = Factories.newEvidence(phase, tribeId, path, fileName, user.getDocument(),
                    file.getContentType());

            evidenceRepository.save(evidence);

            return new ResponseDto<>("Upload successful", HttpStatus.OK.value(), newFileId.toString());
        } catch (IOException e) {
            return new ResponseDto<>("Something was wrong by uploading the file",
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    @Override
    @Cacheable(value = Constants.EVIDENCE_CHECK, key = "#phase + '_' + #tribeId + '_' + #user.document")
    public ResponseDto<Boolean> checkSubmitted(Phase phase, Long tribeId, AuthenticatedUserDto user) {
        boolean exists = evidenceRepository.existsByPhaseAndTribeAndStudent(
                phase,
                tribeId,
                user.getDocument()
        );
        return new ResponseDto<>("Check completed", HttpStatus.OK.value(), exists);
    }

    @Override
    public ResponseDto<String> evaluate(AuthenticatedUserDto authenticatedUser, EvaluationDto evaluationDto) {
        Optional<Teachers> teacher = this.teachersRepository
                .findTeacherByStudentAndEvidence(evaluationDto.getEvidenceId(), authenticatedUser.getDocument(),
                        evaluationDto.getStudent());
        return teacher.map(teachers -> Factories.newEvaluation(teachers, evaluationDto))
                .map(evaluationRepository::save)
                .map(evaluation -> new ResponseDto<>("Evaluation stored successful",
                        HttpStatus.OK.value(), evaluation.getId().toString()))
                .orElse(new ResponseDto<>(Constants.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND.value(), ""));
    }

    @Override
    public ResponseDto<List<IEvidence>> filter(FilterEvidence filter, AuthenticatedUserDto user) {
        Optional<List<IEvidence>> evidences = this.evidenceRepository.filter(
                filter.getGrade(),
                filter.getTribeId(),
                filter.getCourseId(),
                filter.getPhase().toString(),
                user.getDocument());


        return evidences
                .filter(list -> !list.isEmpty())
                .map(list -> new ResponseDto<>("", HttpStatus.OK.value(), list))
                .orElse(new ResponseDto<>(
                        Constants.RESOURCE_NOT_FOUND,
                        HttpStatus.NOT_FOUND.value(),
                        Collections.emptyList()));

    }

    @Override
    public Optional<Tuple<byte[], String>> download(String evidenceId) {
        return this.evidenceRepository
                .findByFileName(evidenceId)
                .map(evidences1 -> Tuple.of(fileStore.downloadFile(evidences1.getPath(),
                        evidences1.getFileName()), evidences1.getFileType()));
    }

    @Data
    @ToString
    @AllArgsConstructor
    public static class FilterEvidence {
        private String grade;
        private Integer tribeId;
        private Integer courseId;
        private Phase phase;
    }
}
