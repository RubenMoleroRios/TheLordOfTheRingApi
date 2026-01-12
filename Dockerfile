
############################################
# Base (dependencias comunes)
############################################
FROM eclipse-temurin:17-jdk-jammy AS base
WORKDIR /app

# curl SOLO para healthcheck
RUN apt-get update \
    && apt-get install -y --no-install-recommends curl \
    && rm -rf /var/lib/apt/lists/*

# Maven wrapper + deps (cacheable)
COPY mvnw .
COPY .mvn/ .mvn/
COPY pom.xml .
RUN ./mvnw dependency:go-offline -B

############################################
# DEV – hot reload (Spring Boot DevTools)
############################################
FROM base AS dev

# Puertos:
#  - APP_PORT → API
#  - 35729    → DevTools LiveReload
EXPOSE 9525 35729

# En DEV no copiamos el código:
# lo monta docker-compose como volumen
CMD ["./mvnw", "spring-boot:run"]

############################################
# BUILD – genera el JAR
############################################
FROM base AS build
COPY src/ src/
RUN ./mvnw clean package -DskipTests

############################################
# PROD – runtime mínimo
############################################
FROM eclipse-temurin:17-jre-jammy AS prod
WORKDIR /app

ENV TZ=Europe/Madrid
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75"

COPY --from=build /app/target/*.jar app.jar

EXPOSE 9525

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar"]
