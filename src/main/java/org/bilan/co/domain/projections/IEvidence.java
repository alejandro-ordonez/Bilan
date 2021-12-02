package org.bilan.co.domain.projections;

public interface IEvidence {
    String getDocument();
    String getName();
    String getLastName();
    String getUploadedDate();
    String getEvidenceId();
    Integer getHasEvaluation();
}
