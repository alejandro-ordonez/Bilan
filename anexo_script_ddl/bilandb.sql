-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         10.5.9-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Volcando estructura de base de datos para bilan
DROP DATABASE IF EXISTS `bilan`;
CREATE DATABASE IF NOT EXISTS `bilan` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `bilan`;

-- Volcando estructura para tabla bilan.actions
DROP TABLE IF EXISTS `actions`;
CREATE TABLE IF NOT EXISTS `actions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `representative` varchar(255) DEFAULT NULL,
  `id_tribe` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK77acqinjdqd14rygajy248pbe` (`id_tribe`),
  CONSTRAINT `FK77acqinjdqd14rygajy248pbe` FOREIGN KEY (`id_tribe`) REFERENCES `tribes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla bilan.activities
DROP TABLE IF EXISTS `activities`;
CREATE TABLE IF NOT EXISTS `activities` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` longtext DEFAULT NULL,
  `id_tribe` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla bilan.answers
DROP TABLE IF EXISTS `answers`;
CREATE TABLE IF NOT EXISTS `answers` (
  `id` int(11) NOT NULL,
  `is_correct` bit(1) DEFAULT NULL,
  `statments` longtext DEFAULT NULL,
  `id_question` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa7obhh0b56p70kf3x0jsfdhog` (`id_question`),
  CONSTRAINT `FKa7obhh0b56p70kf3x0jsfdhog` FOREIGN KEY (`id_question`) REFERENCES `questions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla bilan.challenges
DROP TABLE IF EXISTS `challenges`;
CREATE TABLE IF NOT EXISTS `challenges` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cost` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `reward` int(11) DEFAULT NULL,
  `timer` int(11) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `id_action` int(11) DEFAULT NULL,
  `id_challenge` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK31agy26xd1flj0xq1tc8widrf` (`id_action`),
  KEY `FK3fkvlxmc9h4qmcd39rlsjfu16` (`id_challenge`),
  CONSTRAINT `FK31agy26xd1flj0xq1tc8widrf` FOREIGN KEY (`id_action`) REFERENCES `actions` (`id`),
  CONSTRAINT `FK3fkvlxmc9h4qmcd39rlsjfu16` FOREIGN KEY (`id_challenge`) REFERENCES `student_challenges` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla bilan.classrooms
DROP TABLE IF EXISTS `classrooms`;
CREATE TABLE IF NOT EXISTS `classrooms` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_course` int(11) DEFAULT NULL,
  `id_teacher` int(11) DEFAULT NULL,
  `id_tribe` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK664qowpnthiarvgxsml55oj56` (`id_course`),
  KEY `FK3xwydmgu4p0hftvxhpu9ad6ow` (`id_teacher`),
  KEY `FKsiq79rg758n0bh31nfr7qjdjy` (`id_tribe`),
  CONSTRAINT `FK3xwydmgu4p0hftvxhpu9ad6ow` FOREIGN KEY (`id_teacher`) REFERENCES `teachers` (`id`),
  CONSTRAINT `FK664qowpnthiarvgxsml55oj56` FOREIGN KEY (`id_course`) REFERENCES `courses` (`id`),
  CONSTRAINT `FKsiq79rg758n0bh31nfr7qjdjy` FOREIGN KEY (`id_tribe`) REFERENCES `tribes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla bilan.courses
DROP TABLE IF EXISTS `courses`;
CREATE TABLE IF NOT EXISTS `courses` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `grade` varchar(255) DEFAULT NULL,
  `groups` varchar(255) DEFAULT NULL,
  `school` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla bilan.evidences
DROP TABLE IF EXISTS `evidences`;
CREATE TABLE IF NOT EXISTS `evidences` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `url_file` varchar(255) DEFAULT NULL,
  `id_activiy` int(11) DEFAULT NULL,
  `id_student` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6qpes67q2fywow3qmv6uajme2` (`id_activiy`),
  KEY `FKgni8bumvg6d0fg7ixkw1vw85r` (`id_student`),
  CONSTRAINT `FK6qpes67q2fywow3qmv6uajme2` FOREIGN KEY (`id_activiy`) REFERENCES `activities` (`id`),
  CONSTRAINT `FKgni8bumvg6d0fg7ixkw1vw85r` FOREIGN KEY (`id_student`) REFERENCES `students` (`document`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla bilan.logs
DROP TABLE IF EXISTS `logs`;
CREATE TABLE IF NOT EXISTS `logs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `event` varchar(255) DEFAULT NULL,
  `id_user` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla bilan.questions
DROP TABLE IF EXISTS `questions`;
CREATE TABLE IF NOT EXISTS `questions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `clue_chaman` varchar(255) DEFAULT NULL,
  `difficulty` int(11) DEFAULT NULL,
  `short_statments` longtext DEFAULT NULL,
  `statments` longtext DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `id_challenge` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdxv9tv4vfc56qqlsrt1l4lcdx` (`id_challenge`),
  CONSTRAINT `FKdxv9tv4vfc56qqlsrt1l4lcdx` FOREIGN KEY (`id_challenge`) REFERENCES `challenges` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla bilan.resolved_answer_by
DROP TABLE IF EXISTS `resolved_answer_by`;
CREATE TABLE IF NOT EXISTS `resolved_answer_by` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `id_answer` int(11) DEFAULT NULL,
  `id_challenge` int(11) DEFAULT NULL,
  `id_question` int(11) DEFAULT NULL,
  `id_student` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgam2jw9wrwma7htd9826ibf0j` (`id_answer`),
  KEY `FKme7f57lwr0pca7d59vtwq7rwr` (`id_challenge`),
  KEY `FKrruieb4c86a65e2x8kpb6wt6n` (`id_question`),
  KEY `FKe6rqojln3koyy53xu937km0j1` (`id_student`),
  CONSTRAINT `FKe6rqojln3koyy53xu937km0j1` FOREIGN KEY (`id_student`) REFERENCES `students` (`document`),
  CONSTRAINT `FKgam2jw9wrwma7htd9826ibf0j` FOREIGN KEY (`id_answer`) REFERENCES `answers` (`id`),
  CONSTRAINT `FKme7f57lwr0pca7d59vtwq7rwr` FOREIGN KEY (`id_challenge`) REFERENCES `challenges` (`id`),
  CONSTRAINT `FKrruieb4c86a65e2x8kpb6wt6n` FOREIGN KEY (`id_question`) REFERENCES `questions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla bilan.students
DROP TABLE IF EXISTS `students`;
CREATE TABLE IF NOT EXISTS `students` (
  `document` varchar(255) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `document_type` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `last_state` longtext DEFAULT NULL,
  `modified_at` datetime(6) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`document`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla bilan.student_challenges
DROP TABLE IF EXISTS `student_challenges`;
CREATE TABLE IF NOT EXISTS `student_challenges` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `current_points` int(11) DEFAULT NULL,
  `id_student_stat` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4cjs52cxqb6ix00vt2cm7ow7p` (`id_student_stat`),
  CONSTRAINT `FK4cjs52cxqb6ix00vt2cm7ow7p` FOREIGN KEY (`id_student_stat`) REFERENCES `student_stats` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla bilan.student_stats
DROP TABLE IF EXISTS `student_stats`;
CREATE TABLE IF NOT EXISTS `student_stats` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `analytical_totems` int(11) DEFAULT NULL,
  `critical_totems` int(11) DEFAULT NULL,
  `current_cycle` int(11) DEFAULT NULL,
  `current_spirits` int(11) DEFAULT NULL,
  `general_totems` int(11) DEFAULT NULL,
  `current_cycle_end` datetime(6) NOT NULL,
  `tribes_points` longtext DEFAULT NULL,
  `id_student` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlkkl1qc1vi9jgqp8iuslxwcra` (`id_student`),
  CONSTRAINT `FKlkkl1qc1vi9jgqp8iuslxwcra` FOREIGN KEY (`id_student`) REFERENCES `students` (`document`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla bilan.teachers
DROP TABLE IF EXISTS `teachers`;
CREATE TABLE IF NOT EXISTS `teachers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `document` varchar(255) NOT NULL,
  `document_type` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `id_class` int(11) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `modified_at` datetime(6) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `position_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_geepk44ig3lcjnvm74fa71my0` (`document`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla bilan.tribes
DROP TABLE IF EXISTS `tribes`;
CREATE TABLE IF NOT EXISTS `tribes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `culture` varchar(255) DEFAULT NULL,
  `element` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `adjacent_tribe_id` int(11) DEFAULT NULL,
  `opposite_tribe_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgq0yenh1ngfyylawjil0mluln` (`adjacent_tribe_id`),
  KEY `FKcn8t956y25jux9jrwcys6s5kq` (`opposite_tribe_id`),
  CONSTRAINT `FKcn8t956y25jux9jrwcys6s5kq` FOREIGN KEY (`opposite_tribe_id`) REFERENCES `tribes` (`id`),
  CONSTRAINT `FKgq0yenh1ngfyylawjil0mluln` FOREIGN KEY (`adjacent_tribe_id`) REFERENCES `tribes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


LOAD DATA LOCAL INFILE '/home/Anexo3.csv'
	 INTO TABLE teachers 
   CHARACTER SET utf8
   FIELDS TERMINATED BY ','
	 LINES TERMINATED BY '\n'
	 IGNORE 1 LINES 
	 (@ig1, @ig2, @ig3, @ig4, @ig5, @document_type, document, @ig6, @last_name_1, @last_name_2, @name_1, @name_2,
       @ig7, @ig8, @ig9, email, @ig10, @ig11, @ig12, @ig13, @ig14, @ig15, position_name)
	 SET last_name = CONCAT(TRIM(@last_name_1), " ", TRIM(@last_name_2)), 
	     name = CONCAT(TRIM(@name_1), " ", TRIM(@name_2)),
	     document_type = CASE @document_type WHEN 'Cédula de Ciudania' THEN 'CC' 
                                           WHEN 'Cédula de Extranjeria' THEN 'CE' 
                                           ELSE 'Unknown' END;

-- La exportación de datos fue deseleccionada.

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
