package org.bilan.co.application.evidence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.teacher.EvaluationDto;
import org.bilan.co.domain.dtos.teacher.EvidencesDto;
import org.bilan.co.domain.dtos.user.AuthenticatedUserDto;
import org.bilan.co.domain.entities.*;
import org.bilan.co.domain.enums.BucketName;
import org.bilan.co.domain.enums.Phase;
import org.bilan.co.infraestructure.persistance.EvaluationRepository;
import org.bilan.co.infraestructure.persistance.evidence.EvidenceRepository;
import org.bilan.co.infraestructure.persistance.TeachersRepository;
import org.bilan.co.utils.S3FileStore;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import org.bilan.co.utils.Constants;

@Slf4j
@Component
public class EvidenceService implements IEvidenceService {

    private final S3FileStore fileStore;
    private final EvidenceRepository evidenceRepository;
    private final EvaluationRepository evaluationRepository;
    private final TeachersRepository teachersRepository;

    public EvidenceService(S3FileStore fileStore, EvidenceRepository evidenceRepository,
                           EvaluationRepository evaluationRepository, TeachersRepository teachersRepository) {
        this.fileStore = fileStore;
        this.evidenceRepository = evidenceRepository;
        this.evaluationRepository = evaluationRepository;
        this.teachersRepository = teachersRepository;
    }

    @Override
    public ResponseDto<String> upload(Phase phase, Long tribeId,
                                      MultipartFile file, AuthenticatedUserDto user) {
        String path = String.format("%s/%s", BucketName.BILAN_EVIDENCES.getBucketName(), UUID.randomUUID());
        String fileName = String.format("%s", file.getOriginalFilename());
        try {
            fileStore.upload(path, fileName, Collections.emptyMap(), file.getInputStream());
            Evidences evidence = Factories.newEvidence(phase, tribeId, path, fileName, user.getDocument());
            evidenceRepository.save(evidence);
            return new ResponseDto<>("Upload successful", HttpStatus.OK.value(), evidence.getId().toString());
        } catch (IOException e) {
            return new ResponseDto<>("Something was wrong by uploading the file",
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
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
    public ResponseDto<List<EvidencesDto>> filter(FilterEvidence filter, AuthenticatedUserDto user) {
        return this.evidenceRepository.filter(filter.getGrade(), filter.getTribeId(), filter.getCourseId(),
                        filter.getPhase().toString(), user.getDocument())
                .filter(list -> !list.isEmpty())
                .map(list -> new ResponseDto<>("", HttpStatus.OK.value(), list))
                .orElse(new ResponseDto<>(Constants.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND.value(),
                        Collections.emptyList()));
    }

    @Override
    public byte[] download(Long evidenceId) {
        return this.evidenceRepository
                .findById(evidenceId)
                .map(evidences1 -> fileStore.download(evidences1.getPath(), evidences1.getFileName()))
                .orElse("".getBytes());
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