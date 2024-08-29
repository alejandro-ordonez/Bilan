USE `bilan`;

-- INSERT DEFAULT ADMIN

INSERT INTO `user_info` (`document`, `confirmed`, `created_at`, `document_type`, `email`, `is_enabled`, `last_name`, `modified_at`, `name`, `password`, `position_name`, `role_id`) VALUES ('1024506030', b'1', '2021-10-05 13:00:53.321000', 'CC', 'pruebas1@pruebas1.com', b'1', 'Avendaño', '2021-10-05 13:00:53.321000', 'David', '$2a$12$OSEerOKttf9biSyWnTbXYO/juup8Ax2l17ACO4n3A7cEdW5OMP0ai', 'MEN', 5), ('79443566', b'1', '2024-02-20 13:00:53.321000', 'CC', 'jbenjumea@mineducacion.gov.co', b'1', 'Benjumea', '2024-02-20 13:00:53.321000', 'Jaime', '$2a$12$OSEerOKttf9biSyWnTbXYO/juup8Ax2l17ACO4n3A7cEdW5OMP0ai', 'MEN', 6),('79736107', b'1', '2024-02-20 13:00:53.321000', 'CC', 'jreina@mineducacion.gov.co', b'1', 'Reina', '2024-02-20 13:00:53.321000', 'José', '$2a$12$OSEerOKttf9biSyWnTbXYO/juup8Ax2l17ACO4n3A7cEdW5OMP0ai', 'MEN', 5)

INSERT INTO `user_info` (`document`, `confirmed`, `created_at`, `document_type`, `email`, `is_enabled`, `last_name`, `modified_at`, `name`, `password`, `position_name`, `role_id`) VALUES ('79443566', b'1', '2024-02-20 13:00:53.321000', 'CC', 'jbenjumea@mineducacion.gov.co', b'1', 'Benjumea', '2024-02-20 13:00:53.321000', 'Jaime', '$2a$12$OSEerOKttf9biSyWnTbXYO/juup8Ax2l17ACO4n3A7cEdW5OMP0ai', 'MEN', 6), ('79736107', b'1', '2024-02-20 13:00:53.321000', 'CC', 'jreina@mineducacion.gov.co', b'1', 'Reina', '2024-02-20 13:00:53.321000', 'José', '$2a$12$OSEerOKttf9biSyWnTbXYO/juup8Ax2l17ACO4n3A7cEdW5OMP0ai', 'MEN', 5) 

INSERT INTO `admin` (`document`) VALUES ('79443566'), ('79736107')