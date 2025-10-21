USE `bilan`;

DROP INDEX IF EXISTS idx_resolved_answer_student_tribe_question ON resolved_answer_by;
DROP INDEX IF EXISTS idx_resolved_answer_covering ON resolved_answer_by;
CREATE INDEX idx_resolved_answer_student_question_tribe
ON resolved_answer_by(student_id_document, id_question, tribe_id_id);

DROP INDEX IF EXISTS idx_evidences_tribe_student_phase ON evidences;
DROP INDEX IF EXISTS idx_evidences_student_phase ON evidences;
CREATE INDEX idx_evidences_tribe_student
ON evidences(id_tribe, id_student);
CREATE INDEX idx_evidences_student_tribe
ON evidences(id_student, id_tribe);

DROP INDEX IF EXISTS idx_students_college_grade_course ON students;
CREATE INDEX idx_students_college_grade_course
ON students(college_id, cod_grade, course_id);

DROP INDEX IF EXISTS idx_students_document_college ON students;
CREATE INDEX idx_students_document_college
ON students(document, college_id);

DROP INDEX IF EXISTS idx_evaluation_evidences ON evaluation;
CREATE INDEX idx_evaluation_evidences
ON evaluation(evidences_id);

DROP INDEX IF EXISTS idx_questions_tribe_grade ON questions;
CREATE INDEX idx_questions_tribe_grade
ON questions(id_tribe, grade);

DROP INDEX IF EXISTS idx_answers_question_correct ON answers;
CREATE INDEX idx_answers_question_correct
ON answers(id_question, is_correct);

DROP INDEX IF EXISTS idx_sessions_student_tribe ON sessions;
CREATE INDEX idx_sessions_student_tribe
ON sessions(student_id, tribe_id);

DROP INDEX IF EXISTS idx_sessions_created_student ON sessions;
CREATE INDEX idx_sessions_created_student
ON sessions(created_at, student_id);

DROP INDEX IF EXISTS idx_student_stats_student ON student_stats;
CREATE INDEX idx_student_stats_student
ON student_stats(id_student);

DROP INDEX IF EXISTS idx_student_stats_cycle_end ON student_stats;
CREATE INDEX idx_student_stats_cycle_end
ON student_stats(current_cycle_end, id_student);

DROP INDEX IF EXISTS idx_classrooms_teacher_college ON classrooms;
CREATE INDEX idx_classrooms_teacher_college
ON classrooms(teacher_id, college_id);

DROP INDEX IF EXISTS idx_teachers_college ON teachers;
CREATE INDEX idx_teachers_college
ON teachers(college_id);

DROP INDEX IF EXISTS idx_user_info_role_enabled ON user_info;
CREATE INDEX idx_user_info_role_enabled
ON user_info(role_id, is_enabled);

DROP INDEX IF EXISTS idx_logs_user_created ON logs;
CREATE INDEX idx_logs_user_created
ON logs(id_user, created_at);

DROP INDEX IF EXISTS idx_import_requests_requestor_status ON import_requests;
CREATE INDEX idx_import_requests_requestor_status
ON import_requests(requestor_document, status, created);

DROP INDEX IF EXISTS idx_game_cycles_status_dates ON game_cycles;
CREATE INDEX idx_game_cycles_status_dates
ON game_cycles(game_status, start_date, end_date);

ANALYZE TABLE resolved_answer_by;
ANALYZE TABLE evidences;
ANALYZE TABLE students;
ANALYZE TABLE evaluation;
ANALYZE TABLE questions;
ANALYZE TABLE answers;
ANALYZE TABLE sessions;
ANALYZE TABLE student_stats;
ANALYZE TABLE classrooms;
ANALYZE TABLE teachers;
ANALYZE TABLE colleges;
ANALYZE TABLE user_info;

ALTER TABLE resolved_answer_by STATS_PERSISTENT=1;
ALTER TABLE evidences STATS_PERSISTENT=1;
ALTER TABLE students STATS_PERSISTENT=1;
ALTER TABLE evaluation STATS_PERSISTENT=1;
ALTER TABLE questions STATS_PERSISTENT=1;
ALTER TABLE answers STATS_PERSISTENT=1;
ALTER TABLE sessions STATS_PERSISTENT=1;

ALTER TABLE resolved_answer_by STATS_SAMPLE_PAGES=100;
ALTER TABLE evidences STATS_SAMPLE_PAGES=50;
ALTER TABLE students STATS_SAMPLE_PAGES=50;
