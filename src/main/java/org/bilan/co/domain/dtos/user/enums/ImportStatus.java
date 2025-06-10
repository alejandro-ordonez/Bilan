package org.bilan.co.domain.dtos.user.enums;

public enum ImportStatus {
    ReadyForVerification,
    Verifying,
    ApprovedWithErrors,
    Queued,
    Processing,
    Ok,
    Rejected,
    Failed
}
