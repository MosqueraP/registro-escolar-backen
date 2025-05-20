# Etapa 1: Build
FROM eclipse-temurin:17-jdk AS builder

WORKDIR /app

COPY . .

# Compila el proyecto (sin correr tests)
RUN ./mvnw clean package -DskipTests

# Etapa 2: Imagen final para correr
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copia solo el .jar generado
COPY --from=builder /app/target/*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java", "-jar", "app.jar"]


