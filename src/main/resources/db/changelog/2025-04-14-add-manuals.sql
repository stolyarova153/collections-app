--liquibase formatted sql

--changeset me:1

INSERT INTO roles
VALUES (1, 'admin', 'Администратор', 1),
       (2, 'moderator', 'Модератор', 2),
       (3, 'user', 'Пользователь', 3);

INSERT INTO mediatypes
VALUES (1, 'jpeg', 'image/jpeg'),
       (2, 'plain', 'text/plain'),
       (3, 'csv', 'text/csv'),
       (4, 'pdf', 'application/pdf');
