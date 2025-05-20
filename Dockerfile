# Etapa 1: Build
FROM eclipse-temurin:17-jdk AS builder

WORKDIR /app

COPY . .

# Dar permisos al wrapper
RUN chmod +x ./mvnw

# Compilar el proyecto
RUN ./mvnw clean package -DskipTests

# Etapa 2: Imagen final
FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java", "-jar", "app.jar"]
