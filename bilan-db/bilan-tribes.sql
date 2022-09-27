USE `bilan`;

INSERT INTO bilan.tribes (id,culture,`element`,name) VALUES
	 (1,'Talio','Luz','Competencias Socioemocionales'),
	 (2,'Geodin','Tierra','Lenguaje'),
	 (3,'Warten','Agua','Ciencias Naturales'),
	 (4,'Nebulan','Aire','Competencias Ciudadanas'),
	 (5,'Yarkin','Fuego','Matemáticas');
	 
	 
UPDATE `bilan`.tribes SET adjacent_tribe_id = 2, opposite_tribe_id = 3 WHERE id = 1;
UPDATE `bilan`.tribes SET adjacent_tribe_id = 3, opposite_tribe_id = 4 WHERE id = 2;
UPDATE `bilan`.tribes SET adjacent_tribe_id = 4, opposite_tribe_id = 1 WHERE id = 3;
UPDATE `bilan`.tribes SET adjacent_tribe_id = 1, opposite_tribe_id = 5 WHERE id = 4;
UPDATE `bilan`.tribes SET adjacent_tribe_id = 1, opposite_tribe_id = 2 WHERE id = 5;