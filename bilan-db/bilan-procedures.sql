USE `bilan`;
    
DROP PROCEDURE IF EXISTS `p_statistics_performance_secretary`;
DELIMITER ;;
CREATE PROCEDURE `p_statistics_performance_secretary`()
BEGIN
	SELECT d.departamento AS state
		 , d.tribe_id AS id
		 , d.tribe_name AS name
		 , AVG(d.total_student_tribe)  AS score
		 , AVG(d.scale_basic_competence) AS scoreBasicCompetence
	  FROM v_performance_game_calc_student d
  GROUP BY d.tribe_id
  ORDER BY d.tribe_id;	 
END
;;
DELIMITER ;


DROP PROCEDURE IF EXISTS `p_statistics_performance_secretary_state`;
DELIMITER ;;
CREATE PROCEDURE `p_statistics_performance_secretary_state`(STATE VARCHAR(50))
BEGIN		   
	SELECT d.municipio AS munName
		 , d.dp_id AS munId
		 , d.tribe_id AS id
		 , d.tribe_name AS name
		 , AVG(d.total_student_tribe)  AS score
		 , AVG(d.scale_basic_competence) AS scoreBasicCompetence
	  FROM v_performance_game_calc_student d
     WHERE d.departamento = STATE
  GROUP BY d.tribe_id
  ORDER BY d.tribe_id;
END
;;
DELIMITER ;

DROP PROCEDURE IF EXISTS `p_statistics_performance_secretary_state_mun`;
DELIMITER ;;
CREATE PROCEDURE `p_statistics_performance_secretary_state_mun`(MUN_ID VARCHAR(50))
BEGIN		   
	SELECT d.college_id AS collegeId
		 , CONCAT(d.nombre_establecimiento, ' - ', d.nombre_sede)  AS collegeName
		 , d.tribe_id AS id
		 , d.tribe_name AS name
		 , AVG(d.total_student_tribe)  AS score
		 , AVG(d.scale_basic_competence) AS scoreBasicCompetence
	  FROM v_performance_game_calc_student d
     WHERE d.dp_id = MUN_ID
  GROUP BY d.tribe_id
  ORDER BY d.tribe_id;
END
;;
DELIMITER ;

DROP PROCEDURE IF EXISTS `p_statistics_performance_colleges`;
DELIMITER ;;
CREATE PROCEDURE `p_statistics_performance_colleges`(COLLEGE_ID INT(11))
BEGIN
	SELECT d.tribe_id AS id
		 , d.tribe_name AS name
		 , AVG(d.total_student_tribe)  AS score
		 , AVG(d.scale_basic_competence) AS scoreBasicCompetence
	  FROM v_performance_game_calc_student d
     WHERE d.college_id = COLLEGE_ID
  GROUP BY d.tribe_id
  ORDER BY d.tribe_id;
END
;;
DELIMITER ;

DROP PROCEDURE IF EXISTS `p_statistics_performance_grade`;
DELIMITER ;;
CREATE PROCEDURE `p_statistics_performance_grade`(COLLEGE_ID INT(11), GRADE VARCHAR(10), COURSE_ID INT(11))
BEGIN
       SELECT d.document
       		, d.tribe_id AS id
	   		, d.tribe_name AS name
	   		, d.number_questions_answered AS numberQuestionsAnswered
	   		, AVG(d.total_student_tribe)  AS score
	   		, AVG(d.scale_basic_competence) AS scoreBasicCompetence
	     FROM v_performance_game_calc_student d
	    WHERE d.college_id = COLLEGE_ID
	      AND d.cod_grade = GRADE
	      AND d.course_id = COURSE_ID
	 GROUP BY d.document
 	 ORDER BY d.tribe_id;	 	
END
;;
DELIMITER ;

DROP PROCEDURE IF EXISTS `p_statistics_performance_student`;
DELIMITER ;;
CREATE PROCEDURE `p_statistics_performance_student`(DOCUMENT VARCHAR(10))
BEGIN
	SELECT d.tribe_id AS id
		 , d.tribe_name AS name
		 , d.number_questions_answered AS numberQuestionsAnswered
		 , AVG(d.total_student_tribe)  AS score
		 , AVG(d.scale_basic_competence) AS scoreBasicCompetence
	  FROM v_performance_game_calc_student d
     WHERE d.document = DOCUMENT
  GROUP BY d.tribe_id 
  ORDER BY d.tribe_id;
END
;;
DELIMITER ;

DROP PROCEDURE IF EXISTS `p_statistics_secretary`;
DELIMITER ;;
CREATE PROCEDURE `p_statistics_secretary`()
 BEGIN
	  SELECT r.* 
	  	   ,	r.departamento AS state
  	  	   , r.tribe_id AS id
  		   , r.tribe_name AS name  		   
  	       , CASE 
  	   			WHEN r.tribe_id IN(2,3,5) THEN IFNULL(ROUND(SUM(r.sm_area) / SUM(r.number_evaluations)), 0)
  	   			WHEN r.tribe_id = 1 THEN IFNULL(ROUND(SUM(r.sm_social_emotial) / SUM(r.number_evaluations)), 0)
  	   			WHEN r.tribe_id = 4 THEN IFNULL(ROUND(SUM(r.sm_citizen)  / SUM(r.number_evaluations)), 0)
  	   			ELSE 0
  	     	  END AS score
  	       , IFNULL(ROUND(SUM(r.sm_basic) OVER(PARTITION BY r.document) 
  	            / SUM(r.number_evaluations) OVER(PARTITION BY r.document)), 0) AS scoreBasicCompetence
	    FROM (	 SELECT v.*
	     			  , dp.departamento 
	               FROM v_stadistics v
	               JOIN colleges c 
	                 ON c.id = v.college_id	
	              JOIN departamento_municipio dp 
					  ON c.dep_mun_id = dp.id               
			  ) r
     GROUP BY r.departamento,r.tribe_id
    ORDER BY r.document, r.tribe_id ;	
END
;;
DELIMITER ;

DROP PROCEDURE IF EXISTS `p_statistics_secretary_state`;
DELIMITER ;;
CREATE PROCEDURE `p_statistics_secretary_state`(STATE VARCHAR(50))
 BEGIN
	  SELECT r.municipio AS munName
	  	   , r.id AS munId
	  	   , r.tribe_id AS id
  		   , r.tribe_name AS name  		   
  	       , CASE 
  	   			WHEN r.tribe_id IN(2,3,5) THEN IFNULL(ROUND(SUM(r.sm_area) / SUM(r.number_evaluations)), 0)
  	   			WHEN r.tribe_id IN (1) THEN IFNULL(ROUND(SUM(r.sm_social_emotial) / SUM(r.number_evaluations)), 0)
  	   			WHEN r.tribe_id IN (4) THEN IFNULL(ROUND(SUM(r.sm_citizen)  / SUM(r.number_evaluations)), 0)
  	   			ELSE 0
  	     	  END AS score
  	       , IFNULL(ROUND(SUM(r.sm_basic) OVER(PARTITION BY r.document) 
  	       			/ SUM(r.number_evaluations) OVER(PARTITION BY r.document)), 0) AS scoreBasicCompetence
	    FROM (	 SELECT v.*
	    			  , dp.municipio 
	    			  , dp.id
	               FROM v_stadistics v
	               JOIN colleges c
	                 ON c.id = v.college_id
	               JOIN departamento_municipio dp
	                 ON dp.id = c.dep_mun_id 
                  WHERE dp.departamento = STATE
			  ) r
     GROUP BY r.id, r.tribe_id
    ORDER BY r.document, r.tribe_id ;	
END
;;
DELIMITER ;

DROP PROCEDURE IF EXISTS `p_statistics_secretary_state_mun`;
DELIMITER ;;
CREATE PROCEDURE `p_statistics_secretary_state_mun`(MUN_ID INT(11))
 BEGIN
	  SELECT r.collegeId
	  	   , r.collegeName
	  	   , r.tribe_id AS id
  		   , r.tribe_name AS name  		   
  	       , CASE 
  	   			WHEN r.tribe_id IN(2,3,5) THEN IFNULL(ROUND(SUM(r.sm_area) / SUM(r.number_evaluations)), 0)
  	   			WHEN r.tribe_id IN (1) THEN IFNULL(ROUND(SUM(r.sm_social_emotial) / SUM(r.number_evaluations)), 0)
  	   			WHEN r.tribe_id IN (4) THEN IFNULL(ROUND(SUM(r.sm_citizen)  / SUM(r.number_evaluations)), 0)
  	   			ELSE 0
  	     	  END AS score
  	       , IFNULL(ROUND(SUM(r.sm_basic) OVER(PARTITION BY r.document) 
  	       			/ SUM(r.number_evaluations) OVER(PARTITION BY r.document)), 0) AS scoreBasicCompetence
	    FROM (	 SELECT v.*
	    			  , c.id AS collegeId
		 			  , CONCAT(c.nombre_establecimiento, ' - ', c.nombre_sede)  AS collegeName
	     			  , c.nombre_establecimiento 
	     			  , c.nombre_sede 
	               FROM v_stadistics v
	               JOIN colleges c
	                 ON c.id = v.college_id
	               JOIN departamento_municipio dp
	                 ON dp.id = c.dep_mun_id 
                  WHERE dp.id = MUN_ID
			  ) r
     GROUP BY r.collegeId, r.tribe_id
    ORDER BY r.document, r.tribe_id ;	
END
;;
DELIMITER ;

DROP PROCEDURE IF EXISTS `p_statistics_colleges`;
DELIMITER ;;
CREATE PROCEDURE `p_statistics_colleges`(COLLEGE_ID INT(11))
BEGIN
	  SELECT r.tribe_id AS id
  		   , r.tribe_name AS name
  	       , CASE 
  	   			WHEN r.tribe_id IN(2,3,5) THEN IFNULL(ROUND(SUM(r.sm_area) / SUM(r.number_evaluations)), 0)
  	   			WHEN r.tribe_id IN (1) THEN IFNULL(ROUND(SUM(r.sm_social_emotial) / SUM(r.number_evaluations)), 0)
  	   			WHEN r.tribe_id IN (4) THEN IFNULL(ROUND(SUM(r.sm_citizen)  / SUM(r.number_evaluations)), 0)
  	   			ELSE 0
  	     	  END AS score
  	       , IFNULL(ROUND(SUM(r.sm_basic) OVER(PARTITION BY r.document) 
                / SUM(r.number_evaluations) OVER(PARTITION BY r.document)), 0)  AS scoreBasicCompetence	  
	    FROM (	SELECT *
	              FROM v_stadistics v
 	             WHERE v.college_id = COLLEGE_ID) r
     GROUP BY r.tribe_id;	 
END
;;
DELIMITER ;

DROP PROCEDURE IF EXISTS `p_statistics_grade`;
DELIMITER ;;
CREATE PROCEDURE `p_statistics_grade`(COLLEGE_ID INT(11), GRADE VARCHAR(10), COURSE_ID INT(11))
 BEGIN
	  SELECT r.document
	  	   , r.tribe_id AS id
  		   , r.tribe_name AS name  	
  		   , r.interactive
  		   , r.pre_active AS preActive
  		   , r.post_active AS postActive
  	       , CASE 
  	   			WHEN r.tribe_id IN(2,3,5) THEN IFNULL(ROUND(SUM(r.sm_area) / SUM(r.number_evaluations)), 0)
  	   			WHEN r.tribe_id IN (1) THEN IFNULL(ROUND(SUM(r.sm_social_emotial) / SUM(r.number_evaluations)), 0)
  	   			WHEN r.tribe_id IN (4) THEN IFNULL(ROUND(SUM(r.sm_citizen)  / SUM(r.number_evaluations)), 0)
  	   			ELSE 0
  	     	  END AS score
  	       , IFNULL(ROUND(SUM(r.sm_basic) OVER(PARTITION BY r.document) 
  	       		/ SUM(r.number_evaluations) OVER(PARTITION BY r.document)), 0) AS scoreBasicCompetence
	    FROM (	 SELECT *
	               FROM v_stadistics v
 	              WHERE v.college_id = COLLEGE_ID	
			  	    AND v.cod_grade = GRADE
					AND v.course_id = COURSE_ID
			  ) r
     GROUP BY r.tribe_id,r.document
    ORDER BY r.document, r.tribe_id ;	
END
;;
DELIMITER ;

DROP PROCEDURE IF EXISTS `p_statistics_student`;
DELIMITER ;;
CREATE PROCEDURE `p_statistics_student`(DOCUMENT VARCHAR(10))
 BEGIN
	  SELECT r.document
	  	   , r.tribe_id AS id
  		   , r.tribe_name AS name  
  		   , r.interactive
  		   , r.pre_active AS preActive
  		   , r.post_active AS postActive
  	       , CASE 
  	   			WHEN r.tribe_id IN(2,3,5) THEN IFNULL(ROUND(SUM(r.sm_area) / SUM(r.number_evaluations)), 0)
  	   			WHEN r.tribe_id IN (1) THEN IFNULL(ROUND(SUM(r.sm_social_emotial) / SUM(r.number_evaluations)), 0)
  	   			WHEN r.tribe_id IN (4) THEN IFNULL(ROUND(SUM(r.sm_citizen)  / SUM(r.number_evaluations)), 0)
  	   			ELSE 0
  	     	  END AS score
  	       , IFNULL(ROUND(SUM(r.sm_basic) OVER(PARTITION BY r.document) 
  	       		/ SUM(r.number_evaluations) OVER(PARTITION BY r.document)), 0) AS scoreThinkingCompetence
	    FROM (	 SELECT *
	               FROM v_stadistics v
 	              WHERE v.document = DOCUMENT
			  ) r
     GROUP BY r.tribe_id,r.document
    ORDER BY r.document, r.tribe_id ;	
END
;;
DELIMITER ;

