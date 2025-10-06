#!/bin/bash

# Construir la imagen de Docker
docker build -t api-lotr-image .

# Iniciar los contenedores
docker compose up -d