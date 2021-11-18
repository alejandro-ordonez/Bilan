package org.bilan.co.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BucketName {
    BILAN_EVIDENCES("bilan-evidences");
    private final String bucketName;
}
