USE `bilan`;

-- INSERT DEFAULT ADMIN

INSERT INTO `user_info` (`document`, `confirmed`, `created_at`, `document_type`, `email`, `is_enabled`, `last_name`, `modified_at`, `name`, `password`, `position_name`, `role_id`) VALUES ('1024506030', b'1', '2021-10-05 13:00:53.321000', 'CC', 'pruebas1@pruebas1.com', b'1', 'Avendaño', '2021-10-05 13:00:53.321000', 'David', '$2a$12$OSEerOKttf9biSyWnTbXYO/juup8Ax2l17ACO4n3A7cEdW5OMP0ai', 'MEN', 5);
INSERT INTO `admin` (`document`) VALUES ('1024506030');