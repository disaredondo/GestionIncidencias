# Fase 1: Compilar
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
# Esto guarda las dependencias en caché para el próximo build
RUN mvn -q dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Fase 2: Ejecutar
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# SEGURIDAD: Creamos un usuario sin privilegios y le damos la carpeta
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
COPY --from=build /app/target/*.jar app.jar
RUN chown appuser:appgroup app.jar

# Usamos ese usuario para ejecutar
USER appuser

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]