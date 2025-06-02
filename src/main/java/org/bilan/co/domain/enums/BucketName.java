package org.bilan.co.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BucketName {
    BILAN_EVIDENCES("bilan-evidences"),
    BILAN_TEACHER("bilan-teachers-import"),
    BILAN_TEACHER_ENROLLMENT("bilan-teacher-enrollment"),
    BILAN_STUDENT_IMPORT("bilan-students-import");

    private final String bucketName;
}
