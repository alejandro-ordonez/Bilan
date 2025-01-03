package org.bilan.co.application.evidence;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bilan.co.domain.dtos.teacher.EvaluationDto;
import org.bilan.co.domain.entities.*;
import org.bilan.co.domain.enums.Phase;

import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class Factories {

    public static Evaluation newEvaluation(Teachers teachers, EvaluationDto evaluationDto) {
        Evidences evidences = new Evidences();
        evidences.setId(evaluationDto.getEvidenceId());
        Evaluation evaluation = new Evaluation();
        evaluation.setEvidence(evidences);
        evaluation.setTeacher(teachers);
        evaluation.setCbScore(evaluationDto.getCbScore());
        evaluation.setCcScore(evaluationDto.getCcScore());
        evaluation.setCsScore(evaluationDto.getCsScore());
        evaluation.setTribeScore(evaluationDto.getTribeScore());
        return evaluation;
    }

    public static Evidences newEvidence(Phase phase, Long tribeId, String path, String fileName,
                                        String studentId, String fileType) {
        Students student = new Students();
        student.setDocument(studentId);
        Tribes tribe = new Tribes();
        tribe.setId(tribeId.intValue());
        Evidences evidence = new Evidences();
        evidence.setCreatedAt(new Date());
        evidence.setIdStudent(student);
        evidence.setPath(path);
        evidence.setFileName(fileName);
        evidence.setFileType(fileType);
        evidence.setTribe(tribe);
        evidence.setPhase(phase);
        return evidence;
    }

}
