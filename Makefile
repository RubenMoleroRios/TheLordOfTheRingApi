# ==========================================================
# LOTR API - Makefile
# ==========================================================

# --------- CONFIG ---------
DOCKER_DEV_COMPOSE=docker/dev/docker-compose.yml
DOCKER_PROD_COMPOSE=docker/prod/docker-compose.yml

# --------- DEFAULT ---------
.DEFAULT_GOAL := help

# --------- HELP ---------
help:
	@echo ""
	@echo "LOTR API - Makefile"
	@echo ""
	@echo "Development"
	@echo "  make dev-up           Start DEV environment"
	@echo "  make dev-down         Stop DEV environment"
	@echo "  make dev-down-vol     Stop DEV environment and remove volumes"
	@echo "  make dev-restart      Restart DEV environment"
	@echo "  make dev-logs         Show DEV application logs"
	@echo "  make dev-build        Build DEV images without cache"
	@echo ""
	@echo "Production"
	@echo "  make prod-up          Start PROD environment"
	@echo "  make prod-down        Stop PROD environment"
	@echo "  make prod-down-vol    Stop PROD environment and remove volumes"
	@echo "  make prod-logs        Show PROD application logs"
	@echo ""
	@echo "Cleanup"
	@echo "  make clean            Remove containers, networks and volumes"
	@echo ""

# --------- DEV ---------
dev-up:
	docker compose -f $(DOCKER_DEV_COMPOSE) up -d

dev-down:
	docker compose -f $(DOCKER_DEV_COMPOSE) down

dev-down-vol:
	docker compose -f $(DOCKER_DEV_COMPOSE) down -v

dev-restart:
	docker compose -f $(DOCKER_DEV_COMPOSE) restart

dev-logs:
	docker compose -f $(DOCKER_DEV_COMPOSE) logs -f lotr-app

dev-build:
	docker compose -f $(DOCKER_DEV_COMPOSE) build --no-cache

# --------- PROD ---------
prod-up:
	docker compose -f $(DOCKER_PROD_COMPOSE) up -d

prod-down:
	docker compose -f $(DOCKER_PROD_COMPOSE) down

prod-down-vol:
	docker compose -f $(DOCKER_PROD_COMPOSE) down -v

prod-logs:
	docker compose -f $(DOCKER_PROD_COMPOSE) logs -f lotr-app

# --------- CLEAN ---------
clean:
	docker system prune -af --volumes
