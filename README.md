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
- Autenticación con JWT (Spring Security).
- Registro y login de usuarios con roles (CUSTOMER / ADMIN).
- Protección de endpoints por rol.
- Gestión de usuarios por administración (CRUD, activar/desactivar, roles).
- Gestión de restaurantes (CRUD).
- Secretos de configuración mediante variables de entorno (`.env`).

---

## 🛠 Tecnologías

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- JWT (jjwt)
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
- Registro y login (autenticación)
- Generación y validación de tokens JWT
- Gestión de usuarios (CRUD y reglas de negocio)
- Gestión de restaurantes (CRUD)

---

## 🗺 Roadmap

### ✅ Sprint 1 — Base y CRUD

- Configuración del proyecto
- MySQL
- JPA
- Categorías
- Productos
- Relaciones JPA
- DTO
- Mapper
- Validaciones
- Pruebas unitarias

### ✅ Sprint 2 — Autenticación y seguridad

- Spring Security
- JWT (generación, validación y filtro de autenticación)
- Registro y login de usuarios
- Roles (CUSTOMER / ADMIN)
- Protección de endpoints por rol
- Manejo de errores de autenticación (401 / 409)
- Configuración segura con variables de entorno (`.env`)

### ✅ Sprint 3 — Gestión de usuarios

- CRUD de usuarios (listado, consulta, actualización y eliminación)
- Activación / desactivación de cuentas
- Asignación de roles por administración
- Endpoints protegidos con rol ADMIN
- Regla de negocio: impedir deshabilitar o eliminar la propia cuenta
- Pruebas unitarias del servicio de usuarios

### ✅ Sprint 4 — Gestión de restaurantes

- CRUD de restaurantes (listado, consulta, creación, actualización y eliminación)
- Endpoints de consulta públicos y de escritura protegidos con rol ADMIN
- Validaciones de datos con Bean Validation
- Pruebas unitarias del servicio de restaurantes

### 🚧 Próximamente

- Sprint 5 — Gestión de menú y productos por restaurante
- Docker
- CI/CD
- Despliegue en la nube

---

## 👨‍💻 Autor

Desarrollado por **Ing. Argey Petter Sinisterra Benavides**.

Proyecto desarrollado como parte del proceso de aprendizaje y fortalecimiento del portafolio profesional en Java Backend con Spring Boot.