FROM node:22-alpine AS base
WORKDIR /app

COPY package*.json ./
RUN npm ci

FROM base AS dev
COPY . .
EXPOSE 4200
CMD ["npm", "start"]

FROM base AS build
COPY . .
RUN npm run build

FROM nginx:1.27-alpine AS prod
COPY docker/nginx.conf /etc/nginx/conf.d/default.conf
COPY docker/docker-entrypoint.sh /docker-entrypoint.d/40-write-runtime-config.sh
RUN chmod +x /docker-entrypoint.d/40-write-runtime-config.sh
COPY --from=build /app/dist/frontend/browser /usr/share/nginx/html
EXPOSE 80