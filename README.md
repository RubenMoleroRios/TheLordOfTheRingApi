# The Lord of the Ring API

API REST construida con Spring Boot 3.5 y Java 17 para consultar heroes del universo LOTR, autenticar usuarios con JWT y aplicar RBAC sobre endpoints de administracion.

## Stack

- Java 17
- Spring Boot 3.5.5
- Spring Web
- Spring Data JPA + Hibernate
- Spring Security + JWT
- Flyway
- MySQL
- H2 para algunos tests de soporte
- Testcontainers para integracion y E2E
- OpenAPI / Swagger UI

## Funcionalidad principal

- Registro y login de usuarios con JWT.
- Roles `USER` y `ADMIN` con permisos desacoplados.
- Consulta de heroes, heroes por raza y heroes por bando.
- Administracion de heroes por parte de usuarios con permisos de escritura.
- Administracion de usuarios por parte de administradores.
- Migraciones versionadas con Flyway.

## Arquitectura

La aplicacion esta organizada por modulos y casos de uso:

- `src/main/java/com/ruben/lotr/core/auth`: dominio, casos de uso, persistencia y seguridad de autenticacion.
- `src/main/java/com/ruben/lotr/core/hero`: dominio, casos de uso y adaptadores de heroes.
- `src/main/java/com/ruben/lotr/api/controllers`: capa HTTP.
- `src/main/resources/db/migration`: migraciones principales.
- `src/test/resources/db/test-migration`: esquema y seed de pruebas para integracion/E2E.

## Requisitos

- JDK 17
- Docker Desktop si vas a usar los entornos Docker o los tests con Testcontainers
- Maven Wrapper incluido en el proyecto

## Variables de entorno

La aplicacion principal lee estas variables:

- `APP_NAME`
- `API_CONTAINER_PORT`
- `API_CONTEXT_PATH`
- `DB_HOST`
- `DB_CONTAINER_PORT`
- `DB_NAME`
- `DB_USERNAME`
- `DB_PASSWORD`
- `LOTR_API_TOKEN`
- `JWT_SECRET_KEY`
- `JWT_ACCESS_TOKEN_EXPIRATION_MS`

Valores utiles de referencia:

- `API_CONTAINER_PORT=9525`
- `API_CONTEXT_PATH=/api`
- `JWT_ACCESS_TOKEN_EXPIRATION_MS=3600000`
- `JWT_SECRET_KEY` debe tener suficiente longitud para firmar JWT de forma segura

## Ejecucion local con Maven

Antes de arrancar, exporta las variables necesarias. En PowerShell, por ejemplo:

```powershell
$env:APP_NAME="lotr-api"
$env:API_CONTAINER_PORT="9525"
$env:API_CONTEXT_PATH="/api"
$env:DB_HOST="localhost"
$env:DB_CONTAINER_PORT="3306"
$env:DB_NAME="lotr"
$env:DB_USERNAME="lotr"
$env:DB_PASSWORD="lotr"
$env:LOTR_API_TOKEN="tu_token_externo"
$env:JWT_SECRET_KEY="01234567890123456789012345678901"
$env:JWT_ACCESS_TOKEN_EXPIRATION_MS="3600000"
```

Luego arranca la API con:

```powershell
.\mvnw spring-boot:run
```

## Ejecucion con Docker Compose

El proyecto incluye entornos para desarrollo, test y produccion bajo `docker/`.

Comandos principales del `Makefile`:

```bash
make dev-up
make dev-down
make dev-build
make dev-logs
```

El entorno de desarrollo levanta:

- la API con la imagen `dev`
- MySQL 8.0
- LiveReload y puerto de depuracion `5005`

El `healthcheck` del contenedor apunta a:

```text
http://localhost:9525/api/health
```

## Swagger

Swagger UI esta habilitado y las rutas publicas de documentacion estan abiertas en seguridad.

Si usas `API_CONTEXT_PATH=/api`, la URL habitual sera:

```text
http://localhost:9525/api/swagger-ui/index.html
```

## Endpoints principales

Autenticacion:

- `POST /v1/auth/register`
- `POST /v1/auth/login`
- `POST /v1/auth/logout`

Lectura de heroes:

- `GET /v1/heroes`
- `GET /v1/breeds/{breedId}/heroes`
- `GET /v1/sides/{sideId}/heroes`

Administracion de usuarios:

- `GET /v1/admin/users`
- `GET /v1/admin/users/{id}`
- `POST /v1/admin/users`
- `PUT /v1/admin/users/{id}`
- `DELETE /v1/admin/users/{id}`

Administracion de heroes:

- `POST /v1/admin/heroes`
- `PUT /v1/admin/heroes/{id}`
- `DELETE /v1/admin/heroes/{id}`

Salud:

- `GET /health`

## RBAC

La seguridad usa JWT stateless y permisos materializados como authorities de Spring Security.

Reglas actuales:

- rutas publicas: Swagger, `/health`, `POST /v1/auth/login`, `POST /v1/auth/register`
- lectura de heroes: requiere `HERO_READ`
- lectura de usuarios admin: requiere `USER_READ`
- alta de usuarios admin: requiere `USER_CREATE`
- actualizacion de usuarios admin: requiere `USER_UPDATE`
- borrado de usuarios admin: requiere `USER_DELETE`
- alta de heroes admin: requiere `HERO_CREATE`
- actualizacion de heroes admin: requiere `HERO_UPDATE`
- borrado de heroes admin: requiere `HERO_DELETE`

El registro publico crea usuarios con rol por defecto `USER`.

## Tests

### Maven

Tests unitarios:

```powershell
.\mvnw test
```

Suite completa con unitarios + integracion/E2E:

```powershell
.\mvnw verify
```

Solo integracion/E2E:

```powershell
.\mvnw -DskipUnitTests=true verify
```

Ejecucion puntual de los E2E admin validados recientemente:

```powershell
.\mvnw "-Dmaven.resources.skip=true" "-DskipITs=false" "-Dtest=AdminUsersE2EIT,AdminHeroesE2EIT" clean test
```

### Docker

El contenedor de tests monta el socket Docker para que Testcontainers pueda levantar MySQL real durante las pruebas.

```bash
make test-build
make test-unit
make test-integration
make test-all
make test-jacoco-open
```

## Cobertura

JaCoCo genera datos separados para unit tests e integration tests y los fusiona en la fase `verify`.

Reporte HTML esperado:

```text
target/site/jacoco/index.html
```

## Despliegue

El archivo `render.yaml` define un despliegue web en Render:

- runtime Java
- build command: `./mvnw clean package -DskipTests`
- start command: `java -jar target/*.jar`
- Java 17

## Postman

La coleccion del proyecto esta en:

```text
postman/lotr.postman_collection.json
```

## Estado actual

La integracion RBAC y los E2E admin de usuarios y heroes han quedado operativos sobre el esquema de pruebas actual con Flyway + Testcontainers.