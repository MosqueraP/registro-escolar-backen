# Registro Escolar - Backend API

Este proyecto es un backend RESTful construido con **Spring Boot**, siguiendo una arquitectura en capas y buenas prácticas. Gestiona entidades como estudiantes, profesores, administrativos, cursos e inscripciones.

---

## Tecnologías

- Java 21
- Spring Boot 3+
- Spring Data JPA
- H2 Database (modo archivo)
- Maven
- ModelMapper
- Swagger / OpenAPI
- Docker

---

## Estructura del Proyecto

```bash
├── src/
│   ├── main/java/com/registroescolar/backend/
│   │   ├── controller/          # Controladores REST
│   │   ├── dto/                 # DTOs de transferencia
│   │   ├── exception/           # Excepciones personalizadas
│   │   ├── model/               # Entidades JPA
│   │   ├── repository/          # Repositorios JPA
│   │   └── service/             # Lógica de negocio
├── data.sql                     # Datos iniciales
├── application.properties       # Configuración Spring
├── Dockerfile                   # Imagen Docker
├── pom.xml                      # Dependencias Maven

```

## Construcción y Ejecución con Docker
### Compilar el proyecto:  
```
./mvnw clean package
```


### 2. Crear la imagen Docker

docker build -t registro-escolar-backend .

### 3. Ejecutar el contenedor

## Endpoints disponibles
Swagger disponible en:
```
http://localhost:8080/swagger-ui.html
```

H2 Console (modo desarrollo):
```
http://localhost:8080/h2-console

```
⚠️ Usuario: sa – Password: (vacío)
JDBC URL: jdbc:h2:file:./data/registroescolar

### Pruebas
Se incluyen pruebas unitarias con JUnit para servicios principales. Ejecutar con:

```
./mvnw test

```

### Requisitos Técnicos cumplidos
- Java 11+
- Spring Boot 2.7+
- CRUD completo por entidad
- Validaciones
- Manejo de excepciones con respuestas JSON
- Logs
- Dockerizado
- Documentado con Swagger
- Persistencia en archivo H2
- Filtros
