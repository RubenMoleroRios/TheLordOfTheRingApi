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

CREATE TABLE IF NOT EXISTS `users` (
    `id` BINARY(16) NOT NULL,
    `name` VARCHAR(50) NOT NULL,
    `email` VARCHAR(120) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `role_id` BINARY(16) NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY uk_users_email (`email`)
);

CREATE TABLE IF NOT EXISTS `roles` (
    `id` BINARY(16) NOT NULL,
    `name` VARCHAR(40) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY uk_roles_name (`name`)
);

CREATE TABLE IF NOT EXISTS `permissions` (
    `id` BINARY(16) NOT NULL,
    `name` VARCHAR(60) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY uk_permissions_name (`name`)
);

CREATE TABLE IF NOT EXISTS `role_permissions` (
    `role_id` BINARY(16) NOT NULL,
    `permission_id` BINARY(16) NOT NULL,
    PRIMARY KEY (`role_id`, `permission_id`),

    CONSTRAINT fk_role_permissions_role
        FOREIGN KEY (`role_id`) REFERENCES `roles`(`id`) ON DELETE CASCADE,

    CONSTRAINT fk_role_permissions_permission
        FOREIGN KEY (`permission_id`) REFERENCES `permissions`(`id`) ON DELETE CASCADE
);

ALTER TABLE `users`
    ADD CONSTRAINT fk_users_role
        FOREIGN KEY (`role_id`) REFERENCES `roles`(`id`);

CREATE INDEX idx_users_role_id ON `users` (`role_id`);
