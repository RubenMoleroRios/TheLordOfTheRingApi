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
    ADD COLUMN `role_id` BINARY(16) NULL,
    ADD CONSTRAINT fk_users_role
        FOREIGN KEY (`role_id`) REFERENCES `roles`(`id`);

CREATE INDEX idx_users_role_id ON `users` (`role_id`);