USE `bilan`;

 CREATE OR REPLACE VIEW v_performance_game AS 
   SELECT s.document 
        , s.college_id 
        , s.cod_grade 
        , s.course_id 
        , t.id AS tribe_id
   		, t.name AS tribe_name
   		, IFNULL(IF(SUM(IF(rab.id_answer IS NOT NULL AND a.is_correct = 1, 1, 0)) >= 3, q.title, 0), 0) AS answered
   		, q.p_analytic 
   		, q.p_strategic 
   		, q.p_analytic + q.p_strategic AS total_cp
     FROM tribes t 	 	  
	 JOIN questions q        
       ON t.id = q.id_tribe
     JOIN students s
       ON s.cod_grade = q.grade 
LEFT JOIN resolved_answer_by rab 
	   ON rab.student_id_document = s.document AND rab.id_question = q.id AND rab.tribe_id_id = t.id 
LEFT JOIN answers a  
       ON rab.id_answer = a.id
 GROUP BY s.document, q.id
 ORDER BY s.document;

CREATE OR REPLACE VIEW v_performance_game_calc_student AS 
SELECT c.id AS college_id
	 , c.nombre_establecimiento
	 , c.nombre_sede 
	 , c.codigo_dane_sede 
	 , dp.departamento 
	 , dp.id  AS dp_id
	 , dp.municipio 
	 , r.tribe_id
	 , r.tribe_name
	 , r.cod_grade
	 , r.course_id
	 , r.document
	 , COUNT(r.answered) AS number_questions_answered
	 , SUM(r.answered) AS total_correct_questions
	 , CASE WHEN SUM(r.answered) <= IF(r.tribe_id = 2, 10, 35) THEN 1
       		WHEN SUM(r.answered) <= IF(r.tribe_id = 2, 10, 50) THEN 2
       		WHEN SUM(r.answered) <= IF(r.tribe_id = 2, 20, 65) THEN 3
       	    ELSE 4
       END AS total_student_tribe
     , COUNT(*) OVER (PARTITION BY r.tribe_id) AS number_students
     , SUM(r.total_cp) AS score_basic_competence
     , CASE WHEN SUM(r.total_cp) <= 176 THEN 1
     		WHEN SUM(r.total_cp) <= 251 THEN 2
     		WHEN SUM(r.total_cp) <= 327 THEN 3 
     		ELSE 4
       END AS scale_basic_competence
    FROM v_performance_game r 
    JOIN colleges c 
      ON c.id = r.college_id
    JOIN departamento_municipio dp
      ON dp.id = c.dep_mun_id 
   WHERE r.answered <> 0
GROUP BY r.document;

CREATE OR REPLACE VIEW v_stadistics AS
    SELECT s.document AS document
		, s.college_id 
		, s.cod_grade 
		, s.course_id
   		, t.id AS tribe_id
        , t.name AS tribe_name
        , IFNULL(COUNT(ev.id), 0) number_evaluations
        , IFNULL(SUM(IF(t.id IN (2, 3, 5), ev.tribe_score, 0)), 0) AS sm_area
        , IFNULL(SUM(IF(ev.id, ev.cb_score, 0)), 0) AS sm_basic
        , IFNULL(SUM(IF(t.id IN (2, 3, 5), ev.tribe_score, 0)), 0) sm_area_student				        
        , IFNULL(SUM(IF(t.id IN (1), ev.tribe_score, IF(t.id IN (1), ev.cs_score, 0))), 0) AS sm_social_emotial
        , IFNULL(SUM(IF(t.id IN (4), ev.tribe_score, IF(t.id IN (4), ev.cc_score, 0))), 0) AS sm_citizen
        , SUM(e.id IS NOT NULL AND e.phase = 'INTERACTIVE') AS interactive
        , SUM(e.id IS NOT NULL AND e.phase = 'PRE_ACTIVE')  AS pre_active
        , SUM(e.id IS NOT NULL AND e.phase = 'POST_ACTIVE') AS post_active
      FROM students s  
CROSS JOIN tribes t 
 LEFT JOIN evidences e 	         
        ON t.id = e.id_tribe AND e.id_student = s.document      
 LEFT JOIN evaluation ev
 	    ON ev.evidences_id = e.id 	    
 GROUP BY s.document, t.id 
 ORDER BY s.document, t.id