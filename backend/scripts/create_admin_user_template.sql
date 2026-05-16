-- Script manual para crear el primer administrador.
-- 1. Sustituye los valores de ejemplo.
-- 2. Genera el hash BCrypt de la contraseña antes de ejecutar el INSERT.
-- 3. Ejecuta el script solo después de haber corrido las migraciones V3, V4 y V5.

SET @admin_name = 'Admin';
SET @admin_email = 'admin@example.com';
SET @password_hash = '$2a$10$tdPxu3DZeB880ZNSCFhU0OIjHnm/ZvycCIBXXnJENr6ZlWtznn8JW';

SET @admin_role_id = (
    SELECT id
    FROM roles
    WHERE name = 'ADMIN'
    LIMIT 1
);

INSERT INTO users (id, name, email, password, role_id)
SELECT UUID_TO_BIN(UUID()), @admin_name, @admin_email, @password_hash, @admin_role_id
WHERE @admin_role_id IS NOT NULL;