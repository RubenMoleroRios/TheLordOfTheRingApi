# ==========================================================
# LOTR Frontend - Makefile
# ==========================================================

DOCKER_DEV_COMPOSE=docker/dev/docker-compose.yml
DOCKER_PROD_COMPOSE=docker/prod/docker-compose.yml
DOCKER_IMAGE_NAME=lotr-frontend

.DEFAULT_GOAL := help

help:
	@echo ""
	@echo "LOTR Frontend - Makefile"
	@echo ""
	@echo "Development"
	@echo "  make dev-up       Start DEV frontend"
	@echo "  make dev-down     Stop DEV frontend"
	@echo "  make dev-restart  Restart DEV frontend"
	@echo "  make dev-logs     Show DEV frontend logs"
	@echo "  make dev-build    Build DEV frontend image"
	@echo ""
	@echo "Production"
	@echo "  make prod-build   Build PROD frontend image"
	@echo "  make prod-up      Start PROD frontend"
	@echo "  make prod-down    Stop PROD frontend"
	@echo "  make prod-logs    Show PROD frontend logs"
	@echo ""

dev-up:
	docker compose -f $(DOCKER_DEV_COMPOSE) up -d

dev-down:
	docker compose -f $(DOCKER_DEV_COMPOSE) down

dev-restart:
	docker compose -f $(DOCKER_DEV_COMPOSE) restart

dev-logs:
	docker compose -f $(DOCKER_DEV_COMPOSE) logs -f lotr-frontend

dev-build:
	docker compose -f $(DOCKER_DEV_COMPOSE) build lotr-frontend

prod-build:
	docker build -f docker/Dockerfile --target prod -t $(DOCKER_IMAGE_NAME):prod .

prod-up:
	docker compose -f $(DOCKER_PROD_COMPOSE) up -d

prod-down:
	docker compose -f $(DOCKER_PROD_COMPOSE) down

prod-logs:
	docker compose -f $(DOCKER_PROD_COMPOSE) logs -f lotr-frontend
