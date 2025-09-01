-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         10.5.9-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Volcando estructura de base de datos para bilan
DROP DATABASE IF EXISTS `bilan`;
CREATE DATABASE IF NOT EXISTS `bilan` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `bilan`;


-- bilan.actions definition
DROP TABLE IF EXISTS `actions`;
CREATE TABLE IF NOT EXISTS `actions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- bilan.challenges definition
DROP TABLE IF EXISTS `challenges`;
CREATE TABLE IF NOT EXISTS `challenges` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cost` int DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `timer` int DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- bilan.colleges definition
DROP TABLE IF EXISTS `colleges`;
CREATE TABLE IF NOT EXISTS `colleges` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre_establecimiento` varchar(255) NOT NULL,
  `codigo_dane_sede` varchar(255) NOT NULL,
  `nombre_sede` varchar(255) NOT NULL,
  `dep_mun_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `colleges_codigo_dane_sede_IDX` (`codigo_dane_sede`) USING BTREE,
  KEY `FK_colleges_dep_mun` (`dep_mun_id`),
  CONSTRAINT `FK_colleges_dep_mun` FOREIGN KEY (`dep_mun_id`) REFERENCES `departamento_municipio` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53504 DEFAULT CHARSET=utf8;


-- bilan.contexts definition
DROP TABLE IF EXISTS `contexts`;
CREATE TABLE IF NOT EXISTS `contexts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- bilan.courses definition
DROP TABLE IF EXISTS `courses`;
CREATE TABLE IF NOT EXISTS `courses` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- bilan.logs definition
DROP TABLE IF EXISTS `logs` ;
CREATE TABLE IF NOT EXISTS `logs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `event` varchar(255) DEFAULT NULL,
  `id_user` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- bilan.`privileges` definition
DROP TABLE IF EXISTS `privileges`;
CREATE TABLE IF NOT EXISTS `privileges` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- bilan.roles definition
DROP TABLE IF EXISTS `roles`;
CREATE TABLE IF NOT EXISTS `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- bilan.user_info definition
DROP TABLE IF EXISTS `user_info` ;
CREATE TABLE IF NOT EXISTS `user_info` (
  `document` varchar(255) NOT NULL,
  `confirmed` bit(1) NOT NULL DEFAULT b'0',
  `created_at` datetime(6) DEFAULT NULL,
  `document_type` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_enabled` bit(1) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `position_name` varchar(255) DEFAULT NULL,
  `role_id` int DEFAULT NULL,
  PRIMARY KEY (`document`),
  KEY `FKibudjndsm0mcilkd0jcjgrjp8` (`role_id`),
  CONSTRAINT `FKibudjndsm0mcilkd0jcjgrjp8` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- bilan.admin definition
DROP TABLE IF EXISTS `admin` ;
CREATE TABLE IF NOT EXISTS `admin` (
  `document` varchar(255) NOT NULL,
  PRIMARY KEY (`document`),
  UNIQUE KEY `UK_geepk44ig3lcjnvm74fa71my1` (`document`),
  KEY `FKo1ow4p8hryp4i1sac7d4b5b64` (`document`),
  CONSTRAINT `FKo1ow4p8hryp4i1sac7d4b5b70` FOREIGN KEY (`document`) REFERENCES `user_info` (`document`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- bilan.comment definition
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `author_document` varchar(255) DEFAULT NULL,
  `post_id` int DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_bsykth99gn4hhoqwdaf3wuy67` (`author_document`),
  KEY `FKs1slvnkuemjsq2kj4h3vhx7i1` (`post_id`),
  CONSTRAINT `FK_bsykth99gn4hhoqwdaf3wuy67` FOREIGN KEY (`author_document`) REFERENCES `user_info` (`document`),
  CONSTRAINT `FKs1slvnkuemjsq2kj4h3vhx7i1` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- bilan.min_user definition
DROP TABLE IF EXISTS `min_user`;
CREATE TABLE IF NOT EXISTS `min_user` (
  `document` varchar(255) NOT NULL,
  PRIMARY KEY (`document`),
  CONSTRAINT `FK6q7psgtyo18h2fdr8pqowp44s` FOREIGN KEY (`document`) REFERENCES `user_info` (`document`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- bilan.min_user definition
DROP TABLE IF EXISTS `sec_edu_user`;
CREATE TABLE IF NOT EXISTS `sec_edu_user` (
  `document` varchar(255) NOT NULL,
  `state` varchar(255) NOT NULL,
  PRIMARY KEY (`document`),
  CONSTRAINT `FKSecEduUser_UserInfo` FOREIGN KEY (`document`) REFERENCES `user_info` (`document`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- bilan.post definition
DROP TABLE IF EXISTS `post`;
CREATE TABLE IF NOT EXISTS `post` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `author_document` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKncjpg65eaqqbcsugi4oi3ixmh` (`author_document`),
  CONSTRAINT `FKncjpg65eaqqbcsugi4oi3ixmh` FOREIGN KEY (`author_document`) REFERENCES `user_info` (`document`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- bilan.roles_privileges definition
DROP TABLE IF EXISTS `roles_privileges`;
CREATE TABLE IF NOT EXISTS `roles_privileges` (
  `role_id` int NOT NULL,
  `privilege_id` int NOT NULL,
  KEY `FK5duhoc7rwt8h06avv41o41cfy` (`privilege_id`),
  KEY `FK629oqwrudgp5u7tewl07ayugj` (`role_id`),
  CONSTRAINT `FK5duhoc7rwt8h06avv41o41cfy` FOREIGN KEY (`privilege_id`) REFERENCES `privileges` (`id`),
  CONSTRAINT `FK629oqwrudgp5u7tewl07ayugj` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- bilan.students definition
DROP TABLE IF EXISTS `students`;
CREATE TABLE IF NOT EXISTS `students` (
  `cod_grade` varchar(255) NOT NULL,
  `document` varchar(255) NOT NULL,
  `college_id` int NOT NULL,
  `course_id` int NOT NULL,
  PRIMARY KEY (`document`),
  KEY `FK6jiqckumc6tm0h9otqbtqhgnr` (`course_id`),
  KEY `FK5m8jbc0pre8wg5u8wfgylnfv7` (`college_id`),
  CONSTRAINT `FK5m8jbc0pre8wg5u8wfgylnfv7` FOREIGN KEY (`college_id`) REFERENCES `colleges` (`id`),
  CONSTRAINT `FK6jiqckumc6tm0h9otqbtqhgnr` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`),
  CONSTRAINT `FKo1ow4p8hryp4i1sac7d4b5b63` FOREIGN KEY (`document`) REFERENCES `user_info` (`document`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- bilan.teachers definition
DROP TABLE IF EXISTS `teachers`;
CREATE TABLE IF NOT EXISTS `teachers` (
  `college_id` int NULL,
  `document` varchar(255) NOT NULL,
  PRIMARY KEY (`document`),
  CONSTRAINT `FKlvgrwciawxqmm7hr4spwieg2p` FOREIGN KEY (`document`) REFERENCES `user_info` (`document`),
  CONSTRAINT `FK5m8jbc0pre8wg5u8wfgylnfv556` FOREIGN KEY (`college_id`) REFERENCES `colleges` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- bilan.tribes definition
DROP TABLE IF EXISTS  `tribes`;
CREATE TABLE IF NOT EXISTS `tribes` (
  `id` int NOT NULL AUTO_INCREMENT, 
  `culture` varchar(255) DEFAULT NULL,
  `element` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `adjacent_tribe_id` int DEFAULT NULL,
  `opposite_tribe_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgq0yenh1ngfyylawjil0mluln` (`adjacent_tribe_id`),
  KEY `FKcn8t956y25jux9jrwcys6s5kq` (`opposite_tribe_id`),
  CONSTRAINT `FKcn8t956y25jux9jrwcys6s5kq` FOREIGN KEY (`opposite_tribe_id`) REFERENCES `tribes` (`id`),
  CONSTRAINT `FKgq0yenh1ngfyylawjil0mluln` FOREIGN KEY (`adjacent_tribe_id`) REFERENCES `tribes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6  DEFAULT CHARSET=utf8;

-- bilan.activities definition
DROP TABLE IF EXISTS `activities`;
CREATE TABLE IF NOT EXISTS `activities` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` longtext,
  `id_tribe` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_activities_tribes` (`id_tribe`),
  CONSTRAINT `FK_activities_tribes` FOREIGN KEY (`id_tribe`) REFERENCES `tribes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- bilan.classrooms definition
DROP TABLE IF EXISTS `classrooms`;
CREATE TABLE IF NOT EXISTS `classrooms` (
  `id` int NOT NULL AUTO_INCREMENT,
  `grade` varchar(255) DEFAULT NULL,
  `college_id` int DEFAULT NULL,
  `course_id` int DEFAULT NULL,
  `teacher_id` varchar(255) DEFAULT NULL,
  `tribe_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhyvhtvbi0utt1t8tetxmdcei3` (`college_id`),
  KEY `FKptrcjflhvj48duhcag4f3x9j0` (`course_id`),
  KEY `FKbyl3mbf2j46aa8i242b6gxjg1` (`teacher_id`),
  KEY `FKdenakg0uck9gl13pm2jj9gxxb` (`tribe_id`),
  CONSTRAINT `FKbyl3mbf2j46aa8i242b6gxjg1` FOREIGN KEY (`teacher_id`) REFERENCES `teachers` (`document`),
  CONSTRAINT `FKdenakg0uck9gl13pm2jj9gxxb` FOREIGN KEY (`tribe_id`) REFERENCES `tribes` (`id`),
  CONSTRAINT `FKhyvhtvbi0utt1t8tetxmdcei3` FOREIGN KEY (`college_id`) REFERENCES `colleges` (`id`),
  CONSTRAINT `FKptrcjflhvj48duhcag4f3x9j0` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- bilan.courses_class_rooms definition
DROP TABLE IF EXISTS `courses_class_rooms`;
CREATE TABLE IF NOT EXISTS `courses_class_rooms` (
  `courses_id` int NOT NULL,
  `class_rooms_id` int NOT NULL,
  UNIQUE KEY `UK_dempiwf1f4f74qak4l4p08w9i` (`class_rooms_id`),
  KEY `FKt1vwn14de9tqykpdprgwvptj` (`courses_id`),
  CONSTRAINT `FK87mtnm6y504bn5f3rjvc4mttd` FOREIGN KEY (`class_rooms_id`) REFERENCES `classrooms` (`id`),
  CONSTRAINT `FKt1vwn14de9tqykpdprgwvptj` FOREIGN KEY (`courses_id`) REFERENCES `courses` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- bilan.evidences definition
DROP TABLE IF EXISTS `evidences`;
CREATE TABLE IF NOT EXISTS `evidences` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `file_name` varchar(255) NOT NULL,
  `path` varchar(255) NOT NULL,
  `phase` varchar(255) NOT NULL,
  `id_student` varchar(255) NOT NULL,
  `id_tribe` int NOT NULL,
  `file_type` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UN_evidence_tribe_phase` (`phase`,`id_student`,`id_tribe`),
  KEY `FKgni8bumvg6d0fg7ixkw1vw85r` (`id_student`),
  KEY `FKkuxh3w4eupu203pyeayk0rq5m` (`id_tribe`),
  CONSTRAINT `FKgni8bumvg6d0fg7ixkw1vw85r` FOREIGN KEY (`id_student`) REFERENCES `students` (`document`),
  CONSTRAINT `FKkuxh3w4eupu203pyeayk0rq5m` FOREIGN KEY (`id_tribe`) REFERENCES `tribes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `departamento_municipio`;
CREATE TABLE IF NOT EXISTS `departamento_municipio` (
  `id` int NOT NULL AUTO_INCREMENT,
  `departamento` varchar(255) DEFAULT NULL,
  `cod_dane_municipio` int NOT NULL,
  `municipio` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1121 DEFAULT CHARSET=utf8;


-- bilan.questions definition
DROP TABLE IF EXISTS `questions`;
CREATE TABLE IF NOT EXISTS `questions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `clue_chaman` varchar(255) DEFAULT NULL,
  `difficulty` int DEFAULT NULL,
  `error_message` longtext,
  `grade` varchar(255) DEFAULT NULL,
  `justification` longtext,
  `statement` longtext,
  `p_strategic` float DEFAULT NULL,
  `p_analytic` float DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `context_id` int DEFAULT NULL,
  `id_tribe` int DEFAULT NULL,
  `statements` longtext,
  PRIMARY KEY (`id`),
  KEY `FKgy6d7xm5f7in4astaa0h3k6hr` (`context_id`),
  KEY `FKfsun5pws26kp40rme9ffvgldh` (`id_tribe`),
  CONSTRAINT `FKfsun5pws26kp40rme9ffvgldh` FOREIGN KEY (`id_tribe`) REFERENCES `tribes` (`id`),
  CONSTRAINT `FKgy6d7xm5f7in4astaa0h3k6hr` FOREIGN KEY (`context_id`) REFERENCES `contexts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=utf8;

-- bilan.sessions definition
DROP TABLE IF EXISTS `sessions`;
CREATE TABLE IF NOT EXISTS `sessions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `score` bigint DEFAULT NULL,
  `actions_id` int DEFAULT NULL,
  `challenge_id` int DEFAULT NULL,
  `student_id` varchar(255) DEFAULT NULL,
  `tribe_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKaysvv5w3yu3t7ay0dy0sdmp1v` (`actions_id`),
  KEY `FKbdrb8i34qrh3mrw6w1wq8jdfd` (`challenge_id`),
  KEY `FK5px2wfvd6mo5o51mlt6wt1m2k` (`student_id`),
  KEY `FK37iimewgp3yt5vigw4grgeiw4` (`tribe_id`),
  CONSTRAINT `FK37iimewgp3yt5vigw4grgeiw4` FOREIGN KEY (`tribe_id`) REFERENCES `tribes` (`id`),
  CONSTRAINT `FK5px2wfvd6mo5o51mlt6wt1m2k` FOREIGN KEY (`student_id`) REFERENCES `students` (`document`),
  CONSTRAINT `FKaysvv5w3yu3t7ay0dy0sdmp1v` FOREIGN KEY (`actions_id`) REFERENCES `actions` (`id`),
  CONSTRAINT `FKbdrb8i34qrh3mrw6w1wq8jdfd` FOREIGN KEY (`challenge_id`) REFERENCES `challenges` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- bilan.student_stats definition
DROP TABLE IF EXISTS `student_stats`;
CREATE TABLE IF NOT EXISTS `student_stats` (
  `id` int NOT NULL AUTO_INCREMENT,
  `analytical_totems` int DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `critical_totems` int DEFAULT NULL,
  `current_cycle` int DEFAULT NULL,
  `current_spirits` int DEFAULT NULL,
  `general_totems` int DEFAULT NULL,
  `last_played` datetime(6) DEFAULT NULL,
  `current_cycle_end` datetime(6) NOT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `tribes_balance` varchar(255) DEFAULT NULL,
  `id_student` varchar(255) DEFAULT NULL,
  `time_in_game` float DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlkkl1qc1vi9jgqp8iuslxwcra` (`id_student`),
  CONSTRAINT `FKlkkl1qc1vi9jgqp8iuslxwcra` FOREIGN KEY (`id_student`) REFERENCES `students` (`document`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- bilan.answers definition
DROP TABLE IF EXISTS `answers`;
CREATE TABLE IF NOT EXISTS `answers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `is_correct` bit(1) DEFAULT NULL,
  `statement` longtext,
  `id_question` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa7obhh0b56p70kf3x0jsfdhog` (`id_question`),
  CONSTRAINT `FKa7obhh0b56p70kf3x0jsfdhog` FOREIGN KEY (`id_question`) REFERENCES `questions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=801 DEFAULT CHARSET=utf8;


-- bilan.evaluation definition
DROP TABLE IF EXISTS `evaluation`;
CREATE TABLE IF NOT EXISTS `evaluation` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cb_score` int NOT NULL,
  `cc_score` int NOT NULL,
  `cs_score` int NOT NULL,
  `tribe_score` int NOT NULL,
  `evidences_id` int DEFAULT NULL,
  `teacher_document` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4ijx7cfvxdon49lmhm12vx9es` (`teacher_document`),
  KEY `FK79vm06cm6ddtbk474mngovo38` (`evidences_id`),
  CONSTRAINT `FK4ijx7cfvxdon49lmhm12vx9es` FOREIGN KEY (`teacher_document`) REFERENCES `teachers` (`document`),
  CONSTRAINT `FK79vm06cm6ddtbk474mngovo38` FOREIGN KEY (`evidences_id`) REFERENCES `evidences` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- bilan.resolved_answer_by definition
DROP TABLE IF EXISTS `resolved_answer_by`;
CREATE TABLE IF NOT EXISTS `resolved_answer_by` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `id_answer` int DEFAULT NULL,
  `id_question` int DEFAULT NULL,
  `sessions_id` int DEFAULT NULL,
  `student_id_document` varchar(255) DEFAULT NULL,
  `tribe_id_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgam2jw9wrwma7htd9826ibf0j` (`id_answer`),
  KEY `FKrruieb4c86a65e2x8kpb6wt6n` (`id_question`),
  KEY `FK9dtrk8be13n1y390fqbbg67ic` (`sessions_id`),
  KEY `FKju32i8x9g8vmoinf1rs13o8wt` (`student_id_document`),
  KEY `FK5se91g6y5orsmt2hip1cu3o7j` (`tribe_id_id`),
  CONSTRAINT `FK5se91g6y5orsmt2hip1cu3o7j` FOREIGN KEY (`tribe_id_id`) REFERENCES `tribes` (`id`),
  CONSTRAINT `FK9dtrk8be13n1y390fqbbg67ic` FOREIGN KEY (`sessions_id`) REFERENCES `sessions` (`id`),
  CONSTRAINT `FKgam2jw9wrwma7htd9826ibf0j` FOREIGN KEY (`id_answer`) REFERENCES `answers` (`id`),
  CONSTRAINT `FKju32i8x9g8vmoinf1rs13o8wt` FOREIGN KEY (`student_id_document`) REFERENCES `students` (`document`),
  CONSTRAINT `FKrruieb4c86a65e2x8kpb6wt6n` FOREIGN KEY (`id_question`) REFERENCES `questions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `import_requests`;
CREATE TABLE import_requests (
    import_id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    requestor_document VARCHAR(50) NOT NULL,
    status VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    college_id INT,
    processed INT,
    rejected INT,
    created DATETIME,
    modified DATETIME,
    FOREIGN KEY (requestor_document) REFERENCES user_info(document)
);


DROP TABLE IF EXISTS `game_cycles`;
CREATE TABLE game_cycles (
    game_id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    start_date DATETIME NOT NULL,
    end_date DATETIME NULL,
    closing_requested_by VARCHAR(50) NULL,
    game_status VARCHAR(255) NOT NULL,
    FOREIGN KEY (closing_requested_by) REFERENCES user_info(document)
);
