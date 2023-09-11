package org.bilan.co.utils;

public final class Constants {
    public static final String RESOURCE_NOT_FOUND = "Resource not found";
    public static final String AUTHORIZATION = "Authorization";

    public static final String CACHE_COLLEGES_BY_MUN = "colleges-mun";

    public static final int TOTAL_PHASES = 15;
    public static final int MAX_SIZE_PAGE = 20;

    //Since the question won't vary in the time the number of question will always be 90 per grade
    public static final int TOTAL_QUESTIONS_BY_GRADE = 90;
    public static final String STUDENT = "Estudiante";
    public static final String TEACHER = "Docente";
    public static final String MIN_USER = "Ministerio";
    public static final String SEC_EDU = "Secretaría de Educación";
    public static final String ADMIN = "Administrador";
    public static final String DIREC_TEACHER = "Docente Directivo";
    public static final String DEFAULT_FILE_PATH = "/bilan";
    public static final String ENV_FILE_PATH = "bilan.storage.path";
    public static final String UPLOADED = "UPLOADED";
    public static final String FAILED = "Failed to upload, line: %d";

    private Constants() {
    }
}
