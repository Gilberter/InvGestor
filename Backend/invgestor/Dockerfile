# 1) Build stage
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Copiar solo lo necesario
COPY pom.xml .
COPY src ./src

# Compilar sin tests
RUN mvn -q clean package -DskipTests

# 2) Runtime stage
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copiar el jar generado
COPY --from=build /app/target/*.jar app.jar

# Ejecutar el backend
ENTRYPOINT ["java", "-jar", "app.jar"]
