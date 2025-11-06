# Usa una imagen de Java 17 con JDK
FROM eclipse-temurin:17-jdk-jammy

# Carpeta de trabajo dentro del contenedor
WORKDIR /app

# Copia todos los archivos del proyecto al contenedor
COPY . .

# Compila el proyecto usando Maven del wrapper
RUN ./mvnw clean package -DskipTests

# Expone el puerto en el que corre Spring Boot
EXPOSE 9525

# Comando para ejecutar el JAR generado
CMD ["java", "-jar", "target/thelordofthering-api-0.0.1-SNAPSHOT.jar"]


FROM eclipse-temurin:17-jdk-jammy as builder
WORKDIR /app
# Copiar solo los archivos necesarios para descargar las dependencias
COPY mvnw .
COPY .mvn/ .mvn/
COPY pom.xml .
# Descarga las dependencias
RUN ./mvnw dependency:go-offline -B
# Copiar el código fuente
COPY src/ src/
# Compila la aplicación
RUN ./mvnw clean package -DskipTests

# Etapa de producción
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
# Variables de entorno para la JVM
ENV TZ=Europe/Madrid
ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"
# Copiar el JAR desde la etapa de construcción
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 9525
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar"]