-- Todos los usuarios existentes pasan a ser usuarios normales.
UPDATE `users`
SET `role_id` = UUID_TO_BIN('11f38d4b-a37b-4b92-b62f-e31b838d7885')
WHERE `role_id` IS NULL;

ALTER TABLE `users`
    MODIFY COLUMN `role_id` BINARY(16) NOT NULL;