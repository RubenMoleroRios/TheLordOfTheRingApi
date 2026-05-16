# The Lord of the Ring API Frontend

SPA construida con Angular 21 para autenticación, acceso protegido al dashboard y exploración visual del catálogo de héroes expuesto por la API del proyecto.

## Resumen

- Implementa el cliente web del proyecto sobre Angular standalone.
- Resuelve autenticación, navegación protegida y consumo de la API real.
- Mantiene la configuración del backend desacoplada del build.
- Ofrece una interfaz centrada en consulta, filtrado y detalle de héroes.

## Alcance funcional

- Login y registro desde una misma pantalla.
- Confirmación de registro exitoso.
- Persistencia de sesión en `localStorage`.
- Persistencia temporal de estado en `sessionStorage`.
- Protección de rutas con `authGuard`.
- Consulta inicial de héroes, razas y bandos.
- Filtros por héroe, raza y bando.
- Vista de detalle y cierre de sesión.

## Arquitectura

```text
frontend/
|- src/app/pages/         Pantallas de autenticación, registro exitoso y home
|- src/app/layouts/       Layout público y layout autenticado
|- src/app/components/    Componentes de héroes y filtros
|- src/app/services/      Servicios de autenticación y catálogo
|- src/app/guards/        Guard de acceso autenticado
|- src/app/interceptors/  Interceptor JWT
|- public/                Configuración pública e imágenes
`- docker/                Entornos dev y prod
```

- La zona pública se apoya en `/auth` y `/register-success`.
- La zona privada vive bajo `/app` y requiere autenticación.
- El shell autenticado muestra nombre, correo y acción de cierre de sesión.
- El dashboard resuelve carga inicial y filtros contra la API real.

## Stack técnico

- Angular 21
- TypeScript 5.9
- RxJS 7
- Angular Router
- Angular Forms
- HttpClient con interceptor
- Vitest

## Requisitos

- Node.js compatible con Angular 21.
- npm 10.x o compatible.
- Docker Desktop para entornos contenedorizados.
- Backend disponible, normalmente en `http://localhost:9525/api`.

## Configuración

- Archivo base: `docker/.env.example`
- Puerto HTTP por defecto: `4200`
- API consumida por defecto: `http://localhost:9525/api`
- Configuración pública en `public/browser-config.js`

Variables principales:

- `FRONTEND_IMAGE_NAME=lotr-frontend`
- `FRONTEND_CONTAINER_NAME=lotr-frontend`
- `IMAGE_TAG=latest`
- `FRONTEND_HOST_PORT=4200`
- `API_BASE_URL=http://localhost:9525/api`

Valor actual de referencia del runtime:

```js
window.__appConfig = {
    apiBaseUrl: 'http://localhost:9525/api'
};
```

## Puesta en marcha

### Ejecución local

```powershell
npm install
npm run start
```

URL habitual:

- `http://localhost:4200`

### Ejecución con Docker

El frontend dispone de entornos en `docker/dev` y `docker/prod`.

## Comandos principales

```powershell
make dev-up
make dev-down
make dev-restart
make dev-logs
make dev-build

make prod-build
make prod-up
make prod-down
make prod-logs

npm run start
npm run build
npm run watch
npm run test
```

## Integración y seguridad

### Rutas principales

- `/auth`
- `/register-success`
- `/app`

### Integración con la API

- El frontend consume el backend mediante `API_BASE_URL`.
- El interceptor solo añade `Authorization: Bearer ...` a llamadas dirigidas a la API configurada.
- Las rutas públicas `login` y `register` se excluyen del encabezado de autenticación.
- El dashboard carga héroes, razas y bandos en paralelo para reducir el tiempo inicial de espera.

## Pruebas y calidad

### Frontend

```powershell
npm run test
```

- El proyecto usa Vitest como runner de pruebas.
- `npm run build` genera el artefacto listo para producción.

## Despliegue y operaciones

- Imagen Docker de producción mediante `make prod-build`
- Integración con el monorepo desde la raíz con `make dev-app-complete-up`
- Dependencia directa del backend Spring Boot del mismo repositorio

## Estado actual

El frontend actúa como cliente real de la API: autentica usuarios, mantiene sesión, protege rutas, consume catálogos y ofrece una experiencia centrada en la exploración de héroes sin depender de mocks locales.
