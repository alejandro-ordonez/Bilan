package org.bilan.co.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bilan.co.domain.dtos.user.enums.ImportType;

@AllArgsConstructor
@Getter
public enum BucketName {
    /**
     * Use this folder to store evidences submitted by the students.
     */
    BILAN_EVIDENCES("bilan-evidences"),

    /**
     * Use this folder to store the teacher import request
     */
    BILAN_TEACHER("bilan-teachers-import"),

    /**
     * Use this folder to store the teacher enrollment requests
     */
    BILAN_TEACHER_ENROLLMENT("bilan-teacher-enrollment"),

    /**
     * Use this folder to store the student-enrollment import
     */
    BILAN_STUDENT_IMPORT("bilan-students-import"),

    /**
     * Use this folder to store the colleges import
     */
    BILAN_COLLEGE_IMPORT("bilan-colleges-import"),

    /**
     * Use this folder to store all statistics after finishing a cycle
     */
    BILAN_GAME_CYCLES("bilan-game-cycles");

    private final String bucketName;

    public static BucketName getFromImportType(ImportType type){
        return switch (type){
            case StudentImport -> BILAN_STUDENT_IMPORT;
            case TeacherEnrollment -> BILAN_TEACHER_ENROLLMENT;
            case TeacherImport -> BILAN_TEACHER;
            case CollegesImport -> BILAN_COLLEGE_IMPORT;
        };
    }
}
