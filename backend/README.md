# The Lord of the Ring API Backend

Backend REST construido con Spring Boot 3.5.5 y Java 17 para autenticación JWT, consulta de héroes de la Tierra Media y administración protegida mediante RBAC.

## Resumen

- Expone la API principal del proyecto bajo un contexto HTTP configurable.
- Implementa autenticación stateless con JWT y autorización por authorities.
- Gestiona persistencia, migraciones y documentación OpenAPI.
- Incluye soporte para pruebas unitarias, de integración y E2E.

## Alcance funcional

- Registro público de usuarios.
- Inicio de sesión con emisión de token.
- Logout a nivel de API.
- Consulta de héroes, razas, bandos y detalle individual.
- Administración de usuarios.
- Administración de héroes.
- Endpoint de salud para operaciones y healthchecks.

## Arquitectura

```text
backend/
|- src/main/java/com/ruben/lotr/core/auth       Dominio, casos de uso y seguridad de autenticación
|- src/main/java/com/ruben/lotr/core/hero       Dominio y casos de uso del catálogo de héroes
|- src/main/java/com/ruben/lotr/api/controllers Controladores HTTP
|- src/main/resources/db/migration              Migraciones principales
|- src/test/resources/db/test-migration         Esquema y datos de prueba
|- docker/                                      Entornos dev, test y prod
|- postman/                                     Colección Postman
`- render.yaml                                  Despliegue en Render
```

- La seguridad se apoya en Spring Security y JWT.
- La persistencia usa JPA, Hibernate y MySQL.
- Las migraciones se versionan con Flyway.
- Las pruebas de integración pueden levantar dependencias reales con Testcontainers.

## Stack técnico

- Java 17
- Spring Boot 3.5.5
- Spring Web
- Spring Security
- JWT con `jjwt`
- Spring Data JPA + Hibernate
- Flyway
- MySQL
- H2 para pruebas de soporte
- Testcontainers
- Springdoc OpenAPI / Swagger UI
- JaCoCo

## Requisitos

- JDK 17 para ejecución local fuera de Docker.
- Docker Desktop para entornos Docker y pruebas con Testcontainers.
- Maven Wrapper, ya incluido en el proyecto.
- MySQL disponible si no se usa Docker.

## Configuración

- Archivo base: `docker/.env.example`
- Puerto HTTP por defecto: `9525`
- Context path por defecto: `/api`
- Base de datos por defecto: `lotr`

Variables principales:

- `APP_NAME=thelordofthering-api`
- `APP_HOST_PORT=9525`
- `API_CONTAINER_PORT=9525`
- `API_CONTEXT_PATH=/api`
- `DB_HOST=lotr-db`
- `DB_CONTAINER_PORT=3306`
- `DB_HOST_PORT=3360`
- `DB_NAME=lotr`
- `DB_USERNAME=lotr`
- `DB_PASSWORD=lotr`
- `DB_ROOT_PASSWORD=changeme`
- `JWT_SECRET_KEY=01234567890123456789012345678901`
- `JWT_ACCESS_TOKEN_EXPIRATION_MS=3600000`
- `LOTR_API_TOKEN=change-this-token`

Notas operativas:

- `JWT_SECRET_KEY` debe tener longitud suficiente para firmar tokens de forma segura.
- CORS admite por defecto `http://localhost:4200`, `http://localhost:8080` y `http://localhost`.
- La URL final combina `server.port` y `server.servlet.context-path`.

## Puesta en marcha

### Ejecución local con Maven

Ejemplo de variables en PowerShell:

```powershell
$env:APP_NAME="thelordofthering-api"
$env:API_CONTAINER_PORT="9525"
$env:API_CONTEXT_PATH="/api"
$env:DB_HOST="localhost"
$env:DB_CONTAINER_PORT="3306"
$env:DB_NAME="lotr"
$env:DB_USERNAME="lotr"
$env:DB_PASSWORD="lotr"
$env:LOTR_API_TOKEN="change-this-token"
$env:JWT_SECRET_KEY="01234567890123456789012345678901"
$env:JWT_ACCESS_TOKEN_EXPIRATION_MS="3600000"
```

Arranque:

```powershell
.\mvnw spring-boot:run
```

URLs habituales:

- API base: `http://localhost:9525/api`
- Salud: `http://localhost:9525/api/health`
- Swagger UI: `http://localhost:9525/api/swagger-ui/index.html`

### Ejecución con Docker

El backend dispone de entornos en `docker/dev`, `docker/test` y `docker/prod`.

## Comandos principales

```powershell
make dev-up
make dev-down
make dev-down-vol
make dev-restart
make dev-build
make dev-logs

make test-build
make test-unit
make test-integration
make test-all
make test-jacoco-open

make prod-build
make prod-up
make prod-down
make prod-down-vol
make prod-logs
```

## Integración y seguridad

### Rutas principales

- `POST /v1/auth/register`
- `POST /v1/auth/login`
- `POST /v1/auth/logout`
- `GET /v1/heroes`
- `GET /v1/heroes/{id}`
- `GET /v1/breeds`
- `GET /v1/breeds/{breedId}/heroes`
- `GET /v1/sides`
- `GET /v1/sides/{sideId}/heroes`
- `GET /v1/admin/users`
- `POST /v1/admin/users`
- `POST /v1/admin/heroes`
- `GET /health`

### Control de acceso

- Permisos clave: `HERO_READ`, `HERO_CREATE`, `HERO_UPDATE`, `HERO_DELETE`, `USER_READ`, `USER_CREATE`, `USER_UPDATE`, `USER_DELETE`.
- Rutas públicas principales: Swagger, OpenAPI, `GET /health`, `POST /v1/auth/login` y `POST /v1/auth/register`.
- El registro público crea usuarios con rol por defecto `USER`.

## Pruebas y calidad

### Maven

```powershell
.\mvnw test
.\mvnw verify
.\mvnw -DskipUnitTests=true verify
```

Ejecución puntual de E2E administrativos:

```powershell
.\mvnw "-Dmaven.resources.skip=true" "-DskipITs=false" "-Dtest=AdminUsersE2EIT,AdminHeroesE2EIT" clean test
```

### Cobertura

- JaCoCo fusiona cobertura de pruebas unitarias y de integración en `verify`.
- Reporte HTML: `target/site/jacoco/index.html`

## Despliegue y operaciones

- Despliegue en Render definido en `render.yaml`
- Build command: `./mvnw clean package -Dmaven.test.skip=true`
- Start command: `java -jar target/*.jar`
- Colección Postman disponible en `postman/lotr.postman_collection.json`

## Estado actual

El backend está preparado para operar como servicio principal del monorepo: expone autenticación JWT, catálogo de héroes, administración protegida por RBAC, healthcheck para contenedores y una base de pruebas con cobertura unificada.