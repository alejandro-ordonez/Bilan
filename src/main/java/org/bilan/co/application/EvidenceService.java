package org.bilan.co.application;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.dtos.AuthenticatedUserDto;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.entities.Evidences;
import org.bilan.co.domain.entities.Students;
import org.bilan.co.domain.entities.Tribes;
import org.bilan.co.domain.enums.BucketName;
import org.bilan.co.domain.enums.Phase;
import org.bilan.co.infraestructure.persistance.EvidenceRepository;
import org.bilan.co.utils.S3FileStore;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
public class EvidenceService implements IEvidenceService {

    private final S3FileStore fileStore;
    private final EvidenceRepository evidenceRepository;

    public EvidenceService(S3FileStore fileStore, EvidenceRepository evidenceRepository) {
        this.fileStore = fileStore;
        this.evidenceRepository = evidenceRepository;
    }

    @Override
    public ResponseDto<String> uploadEvidence(Phase phase, Long tribeId,
                                              MultipartFile file, AuthenticatedUserDto user) {
        Objects.requireNonNull(phase);
        Objects.requireNonNull(tribeId);
        Objects.requireNonNull(file);

        String path = String.format("%s/%s", BucketName.BILAN_EVIDENCES.getBucketName(), UUID.randomUUID());
        String fileName = String.format("%s", file.getOriginalFilename());

        try {
            fileStore.upload(path, fileName, Collections.emptyMap(), file.getInputStream());
        } catch (IOException e) {
            log.error("Happened an error uploading the file", e);
        }

        Students student = new Students();
        student.setDocument(user.getDocument());

        Tribes tribe = new Tribes();
        tribe.setId(tribeId.intValue());

        Evidences evidence = new Evidences();
        evidence.setCreatedAt(new Date());
        evidence.setIdStudent(student);
        evidence.setPath(path);
        evidence.setFileName(fileName);
        evidence.setTribe(tribe);
        evidence.setPhase(phase);
        evidenceRepository.save(evidence);

        return new ResponseDto<>("Upload successful", 200, evidence.getId().toString());
    }
}
