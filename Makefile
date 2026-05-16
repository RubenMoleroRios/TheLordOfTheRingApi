# ==========================================================
# LOTR Monorepo - Makefile
# ==========================================================

# --------- CONFIG ---------
BACKEND_DIR=backend
FRONTEND_DIR=frontend

# --------- DEFAULT ---------
.DEFAULT_GOAL := help

# --------- HELP ---------
help:
	@echo ""
	@echo "LOTR Monorepo - Makefile"
	@echo ""
	@echo "Backend"
	@echo "  make backend-dev-up      Start backend DEV environment"
	@echo "  make backend-dev-down    Stop backend DEV environment"
	@echo "  make backend-dev-down-vol Stop backend DEV and remove volumes"
	@echo "  make backend-dev-build   Build backend DEV image"
	@echo "  make backend-dev-logs    Show backend DEV logs"
	@echo "  make backend-test-all    Run backend tests in Docker"
	@echo ""
	@echo "Frontend"
	@echo "  make frontend-dev-up     Start frontend DEV environment"
	@echo "  make frontend-dev-down   Stop frontend DEV environment"
	@echo "  make frontend-dev-build  Build frontend DEV image"
	@echo "  make frontend-dev-logs   Show frontend DEV logs"
	@echo ""
	@echo "Full stack"
	@echo "  make dev-app-complete-up    Start backend + frontend DEV"
	@echo "  make dev-app-complete-down  Stop backend + frontend DEV"
	@echo "  make dev-app-complete-build Build backend + frontend DEV"
	@echo ""
	@echo "Cleanup"
	@echo "  make clean               Remove Docker containers, networks and volumes"
	@echo ""

# --------- BACKEND ---------
backend-dev-up:
	$(MAKE) -C $(BACKEND_DIR) dev-up

backend-dev-down:
	$(MAKE) -C $(BACKEND_DIR) dev-down

backend-dev-down-vol:
	$(MAKE) -C $(BACKEND_DIR) dev-down-vol

backend-dev-build:
	$(MAKE) -C $(BACKEND_DIR) dev-build

backend-dev-logs:
	$(MAKE) -C $(BACKEND_DIR) dev-logs

backend-test-all:
	$(MAKE) -C $(BACKEND_DIR) test-all

# --------- FRONTEND ---------
frontend-dev-up:
	$(MAKE) -C $(FRONTEND_DIR) dev-up

frontend-dev-down:
	$(MAKE) -C $(FRONTEND_DIR) dev-down

frontend-dev-build:
	$(MAKE) -C $(FRONTEND_DIR) dev-build

frontend-dev-logs:
	$(MAKE) -C $(FRONTEND_DIR) dev-logs

# --------- FULL STACK ---------
dev-app-complete-up:
	$(MAKE) -C $(BACKEND_DIR) dev-up
	$(MAKE) -C $(FRONTEND_DIR) dev-up

dev-app-complete-down:
	$(MAKE) -C $(FRONTEND_DIR) dev-down
	$(MAKE) -C $(BACKEND_DIR) dev-down

dev-app-complete-build:
	$(MAKE) -C $(BACKEND_DIR) dev-build
	$(MAKE) -C $(FRONTEND_DIR) dev-build

# --------- CLEAN ---------
clean:
	docker system prune -af --volumes
