# Etapa 1: Build
FROM eclipse-temurin:17-jdk AS builder

WORKDIR /app

# Copiar el código fuente y permisos al wrapper
COPY . .
RUN chmod +x ./mvnw

# Compilar el proyecto sin ejecutar pruebas
RUN ./mvnw clean package -Dmaven.test.skip=true

# Etapa 2: Imagen final para producción
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copiar el JAR desde el builder
COPY --from=builder /app/target/*.jar app.jar

# Render provee estas variables desde el entorno
# (No es necesario declararlas aquí si Render las inyecta)
# ENV SPRING_PROFILES_ACTIVE=prod

#ENTRYPOINT ["java", "-jar", "app.jar"]
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]
