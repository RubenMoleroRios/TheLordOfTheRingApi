# Usa una imagen de Java 17 con JDK
FROM eclipse-temurin:17-jdk-jammy

# Carpeta de trabajo dentro del contenedor
WORKDIR /app

# Copia todos los archivos del proyecto al contenedor
COPY . .

# Compila el proyecto usando Maven del wrapper
RUN ./mvnw clean package -DskipTests

# Expone el puerto en el que corre Spring Boot
EXPOSE 8080

# Comando para ejecutar el JAR generado
CMD ["java", "-jar", "target/thelordofthering-api-0.0.1-SNAPSHOT.jar"]