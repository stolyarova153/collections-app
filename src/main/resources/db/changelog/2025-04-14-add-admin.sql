--liquibase formatted sql

--changeset me:1

INSERT INTO users
VALUES (1, 'Тестовый', 'Администратор', null, '1980-01-01T00:00:00', 'admin@admin.admin', '79000000000', md5('admin'));

INSERT INTO user2role
VALUES (1, 1);
