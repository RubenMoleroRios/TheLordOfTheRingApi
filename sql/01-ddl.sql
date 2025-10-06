CREATE DATABASE IF NOT EXISTS `lotr`;


USE `lotr`;

-- drop table heroes;
-- drop table breed;
-- drop table side;

CREATE TABLE IF NOT EXISTS `side` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(40)
);

CREATE TABLE IF NOT EXISTS `breed` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(40)
);


CREATE TABLE IF NOT EXISTS `heroes` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(40),
    `last_name` VARCHAR(40),
    `height` DOUBLE,
    `hair_color` VARCHAR(30),
    `eyes_color` VARCHAR(30),
    `description` VARCHAR(600), 
    `id_side` BIGINT,
    `id_breed` BIGINT,
    FOREIGN KEY (`id_side`) REFERENCES `side`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`id_breed`) REFERENCES `breed`(`id`) ON DELETE CASCADE
);
