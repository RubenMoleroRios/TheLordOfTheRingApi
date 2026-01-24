CREATE DATABASE IF NOT EXISTS `lotr`;

USE `lotr`;

-- drop table heroes;
-- drop table breed;
-- drop table side;

CREATE TABLE IF NOT EXISTS `side` (
    `id` BINARY(16) PRIMARY KEY,
    `name` VARCHAR(40) NOT NULL
);

CREATE TABLE IF NOT EXISTS `breed` (
    `id` BINARY(16) PRIMARY KEY,
    `name` VARCHAR(40) NOT NULL
);

CREATE TABLE IF NOT EXISTS `heroes` (
    `id` BINARY(16) PRIMARY KEY,
    `name` VARCHAR(40) NOT NULL,
    `last_name` VARCHAR(40),
    `height` DOUBLE,
    `hair_color` VARCHAR(30),
    `eyes_color` VARCHAR(30),
    `description` VARCHAR(600),
    `id_side` BINARY(16) NOT NULL,
    `id_breed` BINARY(16) NOT NULL,

    CONSTRAINT fk_heroes_side
        FOREIGN KEY (`id_side`) REFERENCES `side`(`id`) ON DELETE CASCADE,

    CONSTRAINT fk_heroes_breed
        FOREIGN KEY (`id_breed`) REFERENCES `breed`(`id`) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS users (
    id BINARY(16) NOT NULL,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(120) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_users_email (email)
);
