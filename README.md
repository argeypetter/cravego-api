# 🍔 CraveGo API

Backend REST desarrollado con Spring Boot para la gestión de un sistema de pedidos de comida rápida.

El proyecto implementa una arquitectura por capas siguiendo buenas prácticas de desarrollo, utilizando DTOs, validaciones, documentación con Swagger, pruebas unitarias y manejo global de excepciones.


---
## 📌 Principios aplicados

Durante el desarrollo de CraveGo se aplicaron las siguientes buenas prácticas:

- Arquitectura por capas.
- Separación entre entidades y DTO.
- Patrón Mapper.
- Inyección de dependencias.
- Manejo centralizado de excepciones.
- Validación de datos con Bean Validation.
- Pruebas unitarias con Mockito.
- API REST siguiendo convenciones HTTP.
---

## ✨ Características

- CRUD de categorías.
- CRUD de productos.
- Relación entre productos y categorías.
- Validaciones con Bean Validation.
- Documentación con Swagger OpenAPI.
- Paginación y ordenamiento.
- Manejo global de excepciones.
- Pruebas unitarias con JUnit 5 y Mockito.

---

## 🛠 Tecnologías

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- MySQL
- Maven
- Lombok
- Swagger OpenAPI
- JUnit 5
- Mockito

---

## 🏛 Arquitectura

El proyecto sigue una arquitectura por capas:

```
Controller
    ↓
Service
    ↓
Repository
    ↓
MySQL
```

Cada capa tiene una responsabilidad específica para facilitar el mantenimiento y la escalabilidad del proyecto.

---

## 📂 Estructura del proyecto

```
src
 ├── controller
 ├── dto
 ├── entity
 ├── exception
 ├── mapper
 ├── payload
 ├── repository
 ├── service
 └── config
```

---

## 🚀 Instalación

1. Clonar el repositorio.

```bash
git clone https://github.com/argeypetter/cravego-api.git
```

2. Abrir el proyecto en IntelliJ IDEA.

3. Configurar la base de datos MySQL.

4. Modificar el archivo `application.properties`.

5. Ejecutar la aplicación.

---

## ⚙ Configuración

Configurar la conexión a MySQL:

```properties
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
```

---

## 📖 Documentación

Una vez iniciada la aplicación, la documentación está disponible en:

```
http://localhost:8080/swagger-ui/index.html
```

---

## 🧪 Pruebas

El proyecto incluye pruebas unitarias para la capa de servicios utilizando:

- JUnit 5
- Mockito
- ArgumentCaptor

Las pruebas cubren:

- Consulta
- Creación
- Actualización
- Eliminación
- Manejo de excepciones

---

## 🗺 Roadmap

### ✅ Sprint 1

- Configuración del proyecto
- MySQL
- JPA

### ✅ Sprint 2

- Categorías

### ✅ Sprint 3

- DTO
- Mapper
- Validaciones

### ✅ Sprint 4

- Pruebas unitarias

### ✅ Sprint 5

- Productos
- Relaciones JPA

### 🚧 Próximamente

- Spring Security
- JWT
- Roles
- Docker
- CI/CD
- Despliegue en la nube

---

## 👨‍💻 Autor

Desarrollado por **Ing. Argey Petter Sinisterra Benavides**.

Proyecto desarrollado como parte del proceso de aprendizaje y fortalecimiento del portafolio profesional en Java Backend con Spring Boot.