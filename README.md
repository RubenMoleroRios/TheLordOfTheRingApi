# The Lord of the Ring API

Monorepo full stack con backend en Spring Boot y frontend en Angular para autenticación, exploración de héroes de la Tierra Media y administración protegida mediante RBAC.

## Resumen

- Reúne una API REST y una SPA dentro de un único repositorio.
- Orquesta desarrollo, pruebas y construcción desde la raíz con Make y Docker.
- Centraliza documentación, comandos y configuración del stack completo.
- Mantiene separación clara entre frontend, backend e infraestructura.

## Alcance funcional

- Autenticación con JWT y control de acceso por permisos.
- Catálogo de héroes, razas y bandos.
- Operaciones administrativas sobre usuarios y héroes.
- Dashboard web protegido para consumir la API real.
- Entornos de desarrollo, prueba y producción.

## Arquitectura

```text
TheLordOfTheRingApi/
|- backend/    API REST Spring Boot + Docker + Maven Wrapper + pruebas
|- frontend/   SPA Angular + Docker + configuración pública
|- Makefile    Orquestación del stack completo
`- README.md   Documentación general del monorepo
```

- El frontend consume la API mediante `API_BASE_URL`.
- El backend expone el contexto HTTP bajo `API_CONTEXT_PATH=/api`.
- La integración entre ambos se realiza exclusivamente por contrato HTTP.
- El arranque conjunto en desarrollo levanta backend, frontend y MySQL.

## Stack técnico

### Backend

- Java 17
- Spring Boot 3.5.5
- Spring Security
- Spring Data JPA + Hibernate
- Flyway
- MySQL 8
- JWT
- Testcontainers
- Swagger UI

### Frontend

- Angular 21
- TypeScript 5.9
- RxJS 7
- Angular Router
- Formularios reactivos
- Vitest

### Infraestructura

- Docker
- Docker Compose
- Make
- Render

## Requisitos

- Docker Desktop para entornos contenedorizados y pruebas con Testcontainers.
- Java 17 para ejecutar el backend fuera de Docker.
- Node.js compatible con Angular 21 para ejecutar el frontend fuera de Docker.
- `make` para utilizar los atajos definidos en los Makefile.

## Configuración

### Backend

- Archivo base: `backend/docker/.env.example`
- Puerto HTTP por defecto: `9525`
- Context path por defecto: `/api`
- Base de datos por defecto: MySQL con nombre `lotr`

### Frontend

- Archivo base: `frontend/docker/.env.example`
- Puerto HTTP por defecto: `4200`
- API consumida por defecto: `http://localhost:9525/api`
- Configuración pública en `frontend/public/browser-config.js`

Valor actual de referencia del runtime del cliente:

```js
window.__appConfig = {
    apiBaseUrl: 'http://localhost:9525/api'
};
```

## Puesta en marcha

### Stack completo en desarrollo

Desde la raíz del repositorio:

```powershell
make dev-app-complete-up
```

Servicios esperados:

- API en `http://localhost:9525/api`
- Swagger UI en `http://localhost:9525/api/swagger-ui/index.html`
- Salud en `http://localhost:9525/api/health`
- Frontend en `http://localhost:4200`
- MySQL en `localhost:3360`

Para detener el entorno:

```powershell
make dev-app-complete-down
```

Para reconstruir imágenes de desarrollo:

```powershell
make dev-app-complete-build
```

## Comandos principales

### Raíz

```powershell
make backend-dev-up
make backend-dev-down
make backend-dev-build
make backend-dev-logs
make backend-test-all

make frontend-dev-up
make frontend-dev-down
make frontend-dev-build
make frontend-dev-logs

make dev-app-complete-up
make dev-app-complete-down
make dev-app-complete-build
```

### Backend

```powershell
cd backend
make dev-up
make dev-down
make dev-down-vol
make dev-build
make dev-logs
make test-build
make test-unit
make test-integration
make test-all
make prod-build
make prod-up
make prod-down
```

### Frontend

```powershell
cd frontend
make dev-up
make dev-down
make dev-build
npm install
npm run start
npm run build
npm run test
```

## Integración y seguridad

- El frontend adjunta el token JWT a las peticiones privadas contra la API configurada.
- Las rutas públicas principales incluyen login, registro, salud y documentación OpenAPI.
- Los permisos relevantes incluyen `HERO_READ`, `HERO_CREATE`, `HERO_UPDATE`, `HERO_DELETE`, `USER_READ`, `USER_CREATE`, `USER_UPDATE` y `USER_DELETE`.
- El registro público crea usuarios con rol por defecto `USER`.

## Pruebas y calidad

### Backend

- Pruebas unitarias con Surefire.
- Integración y E2E con Failsafe.
- Base de datos real en pruebas mediante Testcontainers.
- Cobertura unificada en `backend/target/site/jacoco/index.html`.

### Frontend

- Pruebas con `npm run test` usando Vitest.

## Despliegue y operaciones

- Compose de backend: `backend/docker/dev`, `backend/docker/test`, `backend/docker/prod`
- Compose de frontend: `frontend/docker/dev`, `frontend/docker/prod`
- Despliegue del backend preparado en `backend/render.yaml`
- Colección Postman disponible en `postman/lotr.postman_collection.json`

## Estado actual

El monorepo está preparado para desarrollo full stack: la raíz coordina backend y frontend, la API expone autenticación JWT con RBAC y catálogo de héroes, y la SPA consume esos endpoints con rutas protegidas y filtros funcionales.
