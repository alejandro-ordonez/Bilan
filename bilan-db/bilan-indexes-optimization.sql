USE `bilan`;

-- =========================================================================
-- DATABASE INDEX OPTIMIZATION SCRIPT FOR MARIADB
-- Purpose: Add missing indexes to improve query performance for views and procedures
-- MariaDB Version: 10.5.9+
-- =========================================================================

-- -------------------------------------------------------------------------
-- HIGH PRIORITY INDEXES (Critical for performance)
-- -------------------------------------------------------------------------

-- 1. resolved_answer_by: Composite index for v_performance_game view
-- This is THE most critical index - used in the main performance calculation view
DROP INDEX IF EXISTS idx_resolved_answer_student_tribe_question ON resolved_answer_by;
CREATE INDEX idx_resolved_answer_student_tribe_question
ON resolved_answer_by(student_id_document, tribe_id_id, id_question);

-- Alternative covering index that includes the answer (if queries need it)
DROP INDEX IF EXISTS idx_resolved_answer_covering ON resolved_answer_by;
CREATE INDEX idx_resolved_answer_covering
ON resolved_answer_by(student_id_document, tribe_id_id, id_question, id_answer);

-- 2. evidences: Composite index for v_stadistics view
-- Used in LEFT JOIN with students and tribes
DROP INDEX IF EXISTS idx_evidences_tribe_student_phase ON evidences;
CREATE INDEX idx_evidences_tribe_student_phase
ON evidences(id_tribe, id_student, phase);

-- 3. students: Composite index for filtering by college, grade, and course
-- Used in ALL grade-level statistics procedures
DROP INDEX IF EXISTS idx_students_college_grade_course ON students;
CREATE INDEX idx_students_college_grade_course
ON students(college_id, cod_grade, course_id);

-- Additional student index for document lookups with college context
DROP INDEX IF EXISTS idx_students_document_college ON students;
CREATE INDEX idx_students_document_college
ON students(document, college_id);


-- -------------------------------------------------------------------------
-- MEDIUM PRIORITY INDEXES (Significant performance gains)
-- -------------------------------------------------------------------------

-- 4. evaluation: Index on evidences_id for JOIN operations
DROP INDEX IF EXISTS idx_evaluation_evidences ON evaluation;
CREATE INDEX idx_evaluation_evidences
ON evaluation(evidences_id);

-- 5. questions: Composite index for tribe and grade filtering
-- Used when joining with students on grade and filtering by tribe
DROP INDEX IF EXISTS idx_questions_tribe_grade ON questions;
CREATE INDEX idx_questions_tribe_grade
ON questions(id_tribe, grade);

-- 6. answers: Index on id_question for reverse lookups
-- Used in resolved_answer_by JOINs
DROP INDEX IF EXISTS idx_answers_question_correct ON answers;
CREATE INDEX idx_answers_question_correct
ON answers(id_question, is_correct);

-- 7. sessions: Composite index for student and tribe lookups
DROP INDEX IF EXISTS idx_sessions_student_tribe ON sessions;
CREATE INDEX idx_sessions_student_tribe
ON sessions(student_id, tribe_id);

-- Additional session index for date-based queries
DROP INDEX IF EXISTS idx_sessions_created_student ON sessions;
CREATE INDEX idx_sessions_created_student
ON sessions(created_at, student_id);


-- -------------------------------------------------------------------------
-- LOWER PRIORITY INDEXES (Good to have)
-- -------------------------------------------------------------------------

-- 8. student_stats: Index on student for direct lookups
DROP INDEX IF EXISTS idx_student_stats_student ON student_stats;
CREATE INDEX idx_student_stats_student
ON student_stats(id_student);

-- Additional index for active game cycle queries
DROP INDEX IF EXISTS idx_student_stats_cycle_end ON student_stats;
CREATE INDEX idx_student_stats_cycle_end
ON student_stats(current_cycle_end, id_student);

-- 9. classrooms: Composite index for teacher and college lookups
DROP INDEX IF EXISTS idx_classrooms_teacher_college ON classrooms;
CREATE INDEX idx_classrooms_teacher_college
ON classrooms(teacher_id, college_id);

-- 10. teachers: Index on college_id for filtering
DROP INDEX IF EXISTS idx_teachers_college ON teachers;
CREATE INDEX idx_teachers_college
ON teachers(college_id);

-- 11. user_info: Index on role and enabled status for user queries
DROP INDEX IF EXISTS idx_user_info_role_enabled ON user_info;
CREATE INDEX idx_user_info_role_enabled
ON user_info(role_id, is_enabled);

-- 12. evidences: Additional index for student-phase queries without tribe
DROP INDEX IF EXISTS idx_evidences_student_phase ON evidences;
CREATE INDEX idx_evidences_student_phase
ON evidences(id_student, phase);

-- 13. logs: Index for event analysis and user tracking
DROP INDEX IF EXISTS idx_logs_user_created ON logs;
CREATE INDEX idx_logs_user_created
ON logs(id_user, created_at);

-- 14. import_requests: Composite index for requestor and status filtering
DROP INDEX IF EXISTS idx_import_requests_requestor_status ON import_requests;
CREATE INDEX idx_import_requests_requestor_status
ON import_requests(requestor_document, status, created);

-- 15. game_cycles: Index on status and dates for active cycle queries
DROP INDEX IF EXISTS idx_game_cycles_status_dates ON game_cycles;
CREATE INDEX idx_game_cycles_status_dates
ON game_cycles(game_status, start_date, end_date);


-- -------------------------------------------------------------------------
-- ANALYZE TABLES (Update statistics for query optimizer)
-- -------------------------------------------------------------------------

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

-- =========================================================================
-- MARIADB-SPECIFIC OPTIMIZATIONS
-- =========================================================================

-- Enable persistent statistics for better query optimization
ALTER TABLE resolved_answer_by STATS_PERSISTENT=1;
ALTER TABLE evidences STATS_PERSISTENT=1;
ALTER TABLE students STATS_PERSISTENT=1;
ALTER TABLE evaluation STATS_PERSISTENT=1;
ALTER TABLE questions STATS_PERSISTENT=1;
ALTER TABLE answers STATS_PERSISTENT=1;
ALTER TABLE sessions STATS_PERSISTENT=1;

-- Set statistics sample size for more accurate cardinality estimates
ALTER TABLE resolved_answer_by STATS_SAMPLE_PAGES=100;
ALTER TABLE evidences STATS_SAMPLE_PAGES=50;
ALTER TABLE students STATS_SAMPLE_PAGES=50;

-- =========================================================================
-- NOTES:
-- 1. Run this script during low-traffic periods as index creation can lock tables
-- 2. MariaDB supports online DDL for index creation (no table locks on InnoDB)
-- 3. Monitor index usage with:
--    SELECT * FROM information_schema.INDEX_STATISTICS WHERE TABLE_SCHEMA='bilan';
-- 4. Consider adding ALGORITHM=INPLACE, LOCK=NONE for zero-downtime index creation
-- 5. Composite indexes are ordered by selectivity (most selective first)
-- 6. STATS_PERSISTENT ensures statistics survive server restarts
-- =========================================================================
