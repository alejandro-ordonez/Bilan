
-- Volcando estructura de base de datos para bilan
DROP DATABASE IF EXISTS `bilan`;
CREATE DATABASE IF NOT EXISTS `bilan` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `bilan`;

-- ############# ACTIONS ###################

-- Volcando estructura para tabla bilan.actions
CREATE TABLE IF NOT EXISTS `actions` (
  `id` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `id_tribe` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_actions_tribe` (`id_tribe`),
  CONSTRAINT `fk_actions_tribe` FOREIGN KEY (`id_tribe`) REFERENCES `tribes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- Volcando estructura para tabla bilan.activities
DROP TABLE IF EXISTS `activities`;
CREATE TABLE IF NOT EXISTS `activities` (
  `id` int NOT NULL,
  `id_tribe` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `answers`;
CREATE TABLE IF NOT EXISTS  `answers` (
  `id` int NOT NULL,
  `is_correct` bit(1) DEFAULT NULL,
  `id_question` int DEFAULT NULL,
  `statement` longtext,
  PRIMARY KEY (`id`),
  KEY `fk_answers_questions` (`id_question`),
  CONSTRAINT `fk_answers_questions` FOREIGN KEY (`id_question`) REFERENCES `questions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `challenges`;
CREATE TABLE IF NOT EXISTS `challenges` (
  `id` int NOT NULL,
  `cost` int DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `reward` int DEFAULT NULL,
  `timer` int DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `id_action` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_challenges_actions` (`id_action`),
  CONSTRAINT `fk_challenges_actions` FOREIGN KEY (`id_action`) REFERENCES `actions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `classrooms`;
CREATE TABLE IF NOT EXISTS `classrooms` (
  `id` int NOT NULL,
  `groups` varchar(255) DEFAULT NULL,
  `grade` varchar(255) DEFAULT NULL,
  `id_college` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_classrooms_colleges` (`id_college`),
  CONSTRAINT `fk_classrooms_colleges` FOREIGN KEY (`id_college`) REFERENCES `colleges` (`id`),
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `teacher_classrooms`;
CREATE TABLE IF NOT EXISTS `teacher_classrooms` (
  `id` int NOT NULL,
  `id_classroom` int DEFAULT NULL,
  `id_tribe` int DEFAULT NULL,
  `id_teacher` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_teacher_class_room` (`id_classroom`),
  KEY `fk_teacher_classroom_teacher` (`id_teacher`),
  KEY `fk_teacher_classroom_tribe` (`tribe_id`),
  CONSTRAINT `fk_teacher_classroom` FOREIGN KEY (`id_classroom`) REFERENCES `classrooms` (`id`),
  CONSTRAINT `fk_teacher_classroom_teacher` FOREIGN KEY (`id_teacher`) REFERENCES `teachers` (`id`),
  CONSTRAINT `fk_teacher_classroom_tribe` FOREIGN KEY (`id_tribe`) REFERENCES `tribes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `student_classrooms`;
CREATE TABLE IF NOT EXISTS `student_classrooms` (
  `id` int NOT NULL,
  `id_student` int DEFAULT NULL,
  `id_classroom` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_student_classroom_student` (`id_student`),
  KEY `fk_student_classroom_classroom` (`id_classroom`),
  CONSTRAINT `fk_student_classroom_student` FOREIGN KEY (`id_student`) REFERENCES `students` (`document`)
  CONSTRAINT `fk_student_classroom_classroom` FOREIGN KEY (`id_classroom`) REFERENCES `classrooms` (`id`),
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `contexts`;
CREATE TABLE IF NOT EXISTS `contexts` (
  `id` int NOT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `evidences`;
CREATE TABLE IF NOT EXISTS `evidences` (
  `id` int NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `url_file` varchar(255) DEFAULT NULL,
  `id_activiy` int DEFAULT NULL,
  `id_student` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_evidences_activities` (`id_activiy`),
  KEY `fk_evidences_student` (`id_student`),
  CONSTRAINT `fk_evidences_activities` FOREIGN KEY (`id_activiy`) REFERENCES `activities` (`id`),
  CONSTRAINT `fk_evidences_student` FOREIGN KEY (`id_student`) REFERENCES `students` (`document`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `logs`;
CREATE TABLE IF NOT EXISTS `logs` (
  `id` int NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `event` varchar(255) DEFAULT NULL,
  `id_user` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `questions`;
CREATE TABLE IF NOT EXISTS `questions` (
  `id` int NOT NULL,
  `clue_chaman` varchar(255) DEFAULT NULL,
  `difficulty` int DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `grade` varchar(255) DEFAULT NULL,
  `context_id` int DEFAULT NULL,
  `id_tribe` int DEFAULT NULL,
  `justification` longtext,
  `error_message` longtext,
  `statement` longtext,
  `statements` longtext,
  PRIMARY KEY (`id`),
  KEY `fk_questions_context` (`context_id`),
  KEY `fk_questions_tribe` (`id_tribe`),
  CONSTRAINT `fk_questions_tribe` FOREIGN KEY (`id_tribe`) REFERENCES `tribes` (`id`),
  CONSTRAINT `fk_questions_context` FOREIGN KEY (`context_id`) REFERENCES `contexts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `resolved_answer_by`;
CREATE TABLE IF NOT EXISTS `resolved_answer_by` (
  `id` int NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `actions_id` int DEFAULT NULL,
  `id_answer` int DEFAULT NULL,
  `id_challenge` int DEFAULT NULL,
  `id_question` int DEFAULT NULL,
  `id_student` varchar(255) DEFAULT NULL,
  `sessions_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_resolved_answer_by_actions` (`actions_id`),
  KEY `fk_resolved_answer_by_answers` (`id_answer`),
  KEY `fk_resolved_answer_by_challenge` (`id_challenge`),
  KEY `fk_resolved_answer_by_question` (`id_question`),
  KEY `fk_resolved_answer_by_student` (`id_student`),
  KEY `fk_resolved_answer_by_sessions` (`sessions_id`),
  CONSTRAINT `fk_resolved_answer_by_sessions` FOREIGN KEY (`sessions_id`) REFERENCES `sessions` (`id`),
  CONSTRAINT `fk_resolved_answer_by_student` FOREIGN KEY (`id_student`) REFERENCES `students` (`document`),
  CONSTRAINT `fk_resolved_answer_by_answers` FOREIGN KEY (`id_answer`) REFERENCES `answers` (`id`),
  CONSTRAINT `fk_resolved_answer_by_actions` FOREIGN KEY (`actions_id`) REFERENCES `actions` (`id`),
  CONSTRAINT `fk_resolved_answer_by_challenge` FOREIGN KEY (`id_challenge`) REFERENCES `challenges` (`id`),
  CONSTRAINT `fk_resolved_answer_by_question` FOREIGN KEY (`id_question`) REFERENCES `questions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `sessions`;
CREATE TABLE IF NOT EXISTS `sessions` (
  `id` int NOT NULL,
  `score` bigint DEFAULT NULL,
  `actions_id` int DEFAULT NULL,
  `student_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_sessions_actions` (`actions_id`),
  KEY `fk_sessions_student` (`student_id`),
  CONSTRAINT `fk_sessions_student` FOREIGN KEY (`student_id`) REFERENCES `students` (`document`),
  CONSTRAINT `fk_sessions_actions` FOREIGN KEY (`actions_id`) REFERENCES `actions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `student_stats`;
CREATE TABLE IF NOT EXISTS `student_stats` (
  `id` int NOT NULL,
  `analytical_totems` int DEFAULT NULL,
  `critical_totems` int DEFAULT NULL,
  `current_cycle` int DEFAULT NULL,
  `current_spirits` int DEFAULT NULL,
  `general_totems` int DEFAULT NULL,
  `current_cycle_end` datetime(6) NOT NULL,
  `id_student` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_student_stats_student` (`id_student`),
  CONSTRAINT `fk_student_stats_student` FOREIGN KEY (`id_student`) REFERENCES `students` (`document`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `students`;
CREATE TABLE IF NOT EXISTS `students` (
  `document` varchar(255) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `document_type` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `last_state` longtext,
  `modified_at` datetime(6) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `cod_grade` varchar(255) NOT NULL,
  `id_college` int NOT NULL,
  PRIMARY KEY (`document`),
  KEY `fk_students_college` (`id_college`),
  CONSTRAINT `fk_students_college` FOREIGN KEY (`id_college`) REFERENCES `colleges` (`id`),
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `teachers`;
CREATE TABLE IF NOT EXISTS `teachers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `document` varchar(255) NOT NULL,
  `document_type` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `modified_at` datetime(6) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `position_name` varchar(255) DEFAULT NULL,
  `id_college` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_teacher_colleges` (`id_college`),
  CONSTRAINT `fk_teacher_colleges` FOREIGN KEY (`id_college`) REFERENCES `colleges` (`id`),
  UNIQUE KEY `UK_geepk44ig3lcjnvm74fa71my0` (`document`)
) ENGINE=InnoDB AUTO_INCREMENT=330253 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tribes`;
CREATE TABLE IF NOT EXISTS `tribes` (
  `id` int NOT NULL,
  `culture` varchar(255) DEFAULT NULL,
  `element` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `adjacent_tribe_id` int DEFAULT NULL,
  `opposite_tribe_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tribe_adjacent` (`adjacent_tribe_id`),
  KEY `fk_tribe_opposite` (`opposite_tribe_id`),
  CONSTRAINT `fk_tribe_opposite` FOREIGN KEY (`opposite_tribe_id`) REFERENCES `tribes` (`id`),
  CONSTRAINT `fk_tribe_adjacent` FOREIGN KEY (`adjacent_tribe_id`) REFERENCES `tribes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `colleges`;
CREATE TABLE IF NOT EXISTS `colleges` (
  `id` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- Insert Colleges
-- Insert teachers
LOAD DATA LOCAL INFILE '/home/Anexo3.csv'
	 INTO TABLE teachers
   CHARACTER SET utf8
   FIELDS TERMINATED BY ','
	 LINES TERMINATED BY '\n'
	 IGNORE 1 LINES
	 (@ig1, @ig2, @ig3, @ig4, @ig5, @document_type, document, @ig6, @last_name_1, @last_name_2, @name_1, @name_2,
       @ig7, @ig8, @ig9, email, @ig10, @ig11, @ig12, @ig13, @ig14, @ig15, position_name, @ig16, @ig17, @ig18, @ig19,
        @ig20, @ig21, @ig22, @ig23, @ig24, @ig25, id_college)
	 SET last_name = CONCAT(TRIM(@last_name_1), " ", TRIM(@last_name_2)),
	     name = CONCAT(TRIM(@name_1), " ", TRIM(@name_2)),
	     document_type = CASE @document_type WHEN 'Cédula de Ciudania' THEN 'CC'
                                           WHEN 'Cédula de Extranjeria' THEN 'CE'
                                           ELSE 'Unknown' END;

INSERT INTO teachers(document, document_type, last_name, name, position_name, email, created_at, modified_at)
SELECT NRO_DOCUMENTO,
	   CASE TIPO_DOCUMENTO WHEN 'Cédula de Ciudania' THEN 'CC'
                           WHEN 'Cédula de Extranjeria' THEN 'CE'
                           ELSE 'Unknown' END,
        CONCAT(TRIM(APELLIDO1), " ", TRIM(APELLIDO2)),
        CONCAT(TRIM(NOMBRE1), " ", TRIM(NOMBRE2)),
        CARGO,
        EMAIL,
        NOW(),
        NOW()
	FROM Anexo3_csv;
