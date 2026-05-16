SET NAMES utf8mb4;

-- Roles base del sistema.
INSERT INTO `roles` (`id`, `name`) VALUES
    (UUID_TO_BIN('76dce8cb-e89f-4667-8529-bf9e8aa7d0dd'), 'ADMIN'),
    (UUID_TO_BIN('11f38d4b-a37b-4b92-b62f-e31b838d7885'), 'USER');

-- Permisos del catálogo RBAC.
INSERT INTO `permissions` (`id`, `name`) VALUES
    (UUID_TO_BIN('fadb7196-9fea-4fce-ab5d-dbe0c4967db8'), 'HERO_READ'),
    (UUID_TO_BIN('3c270092-4c42-45e7-9165-9690cf67bd20'), 'HERO_CREATE'),
    (UUID_TO_BIN('4ce20850-97a7-4e08-9ce1-e9edc7b6b9a2'), 'HERO_UPDATE'),
    (UUID_TO_BIN('5c2db6b4-9f92-4f95-9b59-2d2d6ab86cc7'), 'HERO_DELETE'),
    (UUID_TO_BIN('75f71649-b3c5-4dba-af45-f8be5644d94e'), 'USER_READ'),
    (UUID_TO_BIN('6839b938-e04b-4f16-a854-f8f4c1b9a5d7'), 'USER_CREATE'),
    (UUID_TO_BIN('03ed1bc0-441a-4743-8d7f-0edabf1b26b3'), 'USER_UPDATE'),
    (UUID_TO_BIN('763d494f-94e6-4b76-80c6-0e0aa0c5f0bc'), 'USER_DELETE'),
    (UUID_TO_BIN('7f4551b5-fb0b-48fd-8f1a-7af9d29bb640'), 'ADMIN_CREATE');

-- El rol USER puede autenticarse y consultar héroes.
INSERT INTO `role_permissions` (`role_id`, `permission_id`) VALUES
    (UUID_TO_BIN('11f38d4b-a37b-4b92-b62f-e31b838d7885'), UUID_TO_BIN('fadb7196-9fea-4fce-ab5d-dbe0c4967db8'));

-- El rol ADMIN hereda todos los permisos operativos del sistema.
INSERT INTO `role_permissions` (`role_id`, `permission_id`) VALUES
    (UUID_TO_BIN('76dce8cb-e89f-4667-8529-bf9e8aa7d0dd'), UUID_TO_BIN('fadb7196-9fea-4fce-ab5d-dbe0c4967db8')),
    (UUID_TO_BIN('76dce8cb-e89f-4667-8529-bf9e8aa7d0dd'), UUID_TO_BIN('3c270092-4c42-45e7-9165-9690cf67bd20')),
    (UUID_TO_BIN('76dce8cb-e89f-4667-8529-bf9e8aa7d0dd'), UUID_TO_BIN('4ce20850-97a7-4e08-9ce1-e9edc7b6b9a2')),
    (UUID_TO_BIN('76dce8cb-e89f-4667-8529-bf9e8aa7d0dd'), UUID_TO_BIN('5c2db6b4-9f92-4f95-9b59-2d2d6ab86cc7')),
    (UUID_TO_BIN('76dce8cb-e89f-4667-8529-bf9e8aa7d0dd'), UUID_TO_BIN('75f71649-b3c5-4dba-af45-f8be5644d94e')),
    (UUID_TO_BIN('76dce8cb-e89f-4667-8529-bf9e8aa7d0dd'), UUID_TO_BIN('6839b938-e04b-4f16-a854-f8f4c1b9a5d7')),
    (UUID_TO_BIN('76dce8cb-e89f-4667-8529-bf9e8aa7d0dd'), UUID_TO_BIN('03ed1bc0-441a-4743-8d7f-0edabf1b26b3')),
    (UUID_TO_BIN('76dce8cb-e89f-4667-8529-bf9e8aa7d0dd'), UUID_TO_BIN('763d494f-94e6-4b76-80c6-0e0aa0c5f0bc')),
    (UUID_TO_BIN('76dce8cb-e89f-4667-8529-bf9e8aa7d0dd'), UUID_TO_BIN('7f4551b5-fb0b-48fd-8f1a-7af9d29bb640'));