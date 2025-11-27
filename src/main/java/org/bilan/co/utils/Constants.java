package org.bilan.co.utils;

public final class Constants {
    public static final String RESOURCE_NOT_FOUND = "Resource not found";
    public static final String AUTHORIZATION = "Authorization";

    public static final String CACHE_COLLEGES_BY_MUN = "colleges-mun";
    public static final String COLLEGES_BY_STATE = "colleges-by-state";
    public static final String GRADES_COURSES = "grades-courses";
    public static final String GAME_INFO = "game-info";
    public static final String GAME_CYCLE = "game-cycle";
    public static final String STATES = "states";
    public static final String CITIES = "cities";
    public static final String COURSES = "courses";

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
    public static final String STAGED_PATH = "Staged";
    public static final String FAILED_PATH = "Failed";
    public static final String QUEUED_PATH = "Queued";
    public static final String SUCCESS_PATH = "Success";
    public static final String ENV_FILE_PATH = "bilan.storage.path";
    public static final String UPLOADED = "UPLOADED";
    public static final String FAILED = "Failed to upload, line: %d";
    public static final String Ok = "Ok";
    public static final String JSON = ".json";
    public static final String CSV = ".csv";
    public static final String GENERAL_STATISTICS = "general-statistics";
    public static final String STATE_STATISTICS = "state-statistics";
    public static final String MUNICIPALITY_STATISTICS = "municipality-statistics";
    public static final String COLLEGE_STATISTICS = "college-statistics";
    public static final String GRADE_STATISTICS = "grade-statistics";
    public static final String STUDENT_STATISTICS = "student-statistics";
    public static final String EVIDENCE_CHECK = "evidence-check";

    // Headers
    public static final String StudentImportHeaders = String.join(", ","Documento", "Tipo de documento", "Nombre", "Apellido", "Grado", "Curso");
    public static final String TeacherImportHeaders = String.join(", ","Documento", "Tipo de documento", "Email", "Nombre", "Apellido");
    public static final String TeacherEnrollmentHeaders = String.join(", ","Documento", "Tipo de documento", "Grado", "Curso", "Tribu");
    public static final String CollegeImportHeaders = String.join(", ","Departamento", "Cod dane municipio", "Nombre", "Cod dane sede", "Nombre sede");

    private Constants() {
    }
}
