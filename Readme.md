# 🩸 Sistema de Gestión de Donación de Sangre

## 📌 Descripción del Proyecto

Este proyecto consiste en una aplicación web orientada a la gestión de procesos relacionados con la donación de sangre. El sistema permite administrar donantes, registrar donaciones, controlar el inventario sanguíneo y manejar autenticación de usuarios mediante una API desarrollada con Spring Boot y un cliente web construido con HTML, CSS y JavaScript.

El proyecto está dividido en dos partes principales:

- **AppDonacion** → Backend desarrollado con Spring Boot.
- **Banco_Sangre_Consumer** → Frontend consumidor de la API.

---

# ⚙️ Tecnologías Utilizadas

## 🔹 Backend - AppDonacion

- Java 17
- Spring Boot 3.3.5
- Spring Web
- Spring Data JPA
- Spring Security
- JWT (JSON Web Token)
- MySQL
- Lombok
- MapStruct
- Swagger / OpenAPI
- Maven
- Docker

## 🔹 Frontend - Banco_Sangre_Consumer

- HTML5
- CSS3
- JavaScript
- Fetch API

---

# 📂 Estructura General del Proyecto

```text
AppDonacion/
│
├── src/main/java/
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── model/
│   ├── dto/
│   ├── config/
│   └── security/
│
├── src/main/resources/
│   ├── application.properties
│   └── static/
│
├── Dockerfile
├── pom.xml
└── mvnw

Banco_Sangre_Consumer/
│
├── css/
├── js/
├── images/
├── index.html
├── donantes.html
├── donaciones.html
└── inventario.html
```

---

# 🚀 Funcionalidades Principales

## 👤 Gestión de Donantes

- Registro de donantes.
- Consulta de donantes.
- Filtrado de donantes.
- Validaciones de información.

## 🩸 Gestión de Donaciones

- Registro de donaciones.
- Asociación entre donante y donación.
- Consulta de historial.

## 🧪 Inventario de Sangre

- Control de tipos sanguíneos.
- Actualización automática del inventario.
- Visualización de cantidades disponibles.

## 🔐 Seguridad y Autenticación

- Inicio de sesión.
- Registro de usuarios.
- Protección de endpoints.
- Uso de JWT para autenticación.
- Manejo de sesiones en frontend.

---

# 🛠️ Configuración del Backend

## 📌 Requisitos

- Java 17
- Maven
- MySQL
- Docker (opcional)

---

## 📌 Configuración de Base de Datos

Modificar el archivo:

```properties
src/main/resources/application.properties
```

Ejemplo:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/app_donacion
spring.datasource.username=root
spring.datasource.password=tu_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## ▶️ Ejecutar el Proyecto

### Con Maven

```bash
mvn spring-boot:run
```

### Generar JAR

```bash
mvn clean package
```

### Ejecutar JAR

```bash
java -jar target/AppDonacion-0.0.1-SNAPSHOT.jar
```

---

# 🐳 Docker

## Construir imagen

```bash
docker build -t appdonacion .
```

## Ejecutar contenedor

```bash
docker run -p 8080:8080 appdonacion
```

---

# 🌐 Frontend

El frontend consume la API REST desarrollada en Spring Boot mediante `fetch()`.

## 📌 Funcionalidades del Cliente

- Login.
- Registro.
- Consulta de donantes.
- Registro de donaciones.
- Visualización de inventario.
- Manejo de tokens.
- Control de cierre de sesión.

---

# 🔗 Endpoints Principales

## Auth

```http
POST /api/auth/login
POST /api/auth/register
```

## Donantes

```http
GET /api/donantes
POST /api/donantes
PUT /api/donantes/{id}
DELETE /api/donantes/{id}
```

## Donaciones

```http
GET /api/donaciones
POST /api/donaciones
```

## Inventario

```http
GET /api/inventario
```

---

# 📖 Documentación Swagger

Cuando el proyecto esté ejecutándose:

```text
http://localhost:8080/swagger-ui.html
```

O:

```text
http://localhost:8080/swagger-ui/index.html
```

---

# 🔒 Seguridad

El sistema utiliza Spring Security junto con JWT para proteger los endpoints.

Características:

- Generación de tokens JWT.
- Validación de autenticación.
- Protección de rutas.
- Manejo de autorización.

---

# 📸 Interfaces del Frontend

El cliente web cuenta con:

- Página de inicio.
- Login.
- Registro.
- Gestión de donantes.
- Gestión de donaciones.
- Inventario de sangre.

---

## ☁️ Despliegue y Hosting

El proyecto fue desplegado utilizando plataformas cloud modernas para separar el frontend y el backend, permitiendo una arquitectura más organizada y escalable.

### 🌐 Frontend - Vercel

El cliente web fue desplegado en Vercel, permitiendo servir la aplicación estática de manera rápida y optimizada.

**URL del Frontend:**

[AppDonacion Frontend - Vercel](https://app-donacion-frkmsb9af-appsbase.vercel.app?_vercel_share=LtMC6WzUvfViAcgFBli2XMICnIeNHWzS&utm_source=chatgpt.com)

Características del despliegue:

* Hosting automático para aplicaciones frontend.
* Integración sencilla con proyectos HTML, CSS y JavaScript.
* Despliegue continuo.
* CDN global para mejor rendimiento.
* Acceso público desde navegador.

---

### 🚂 Backend - Railway

La API REST desarrollada con Spring Boot fue desplegada en Railway, permitiendo mantener el backend disponible en la nube.

**URL del Backend (Swagger):**

[AppDonacion API - Railway Swagger](https://app-donacion-production.up.railway.app/swagger-ui/index.html?utm_source=chatgpt.com)

Características del despliegue:

* Ejecución de aplicaciones Spring Boot en contenedores.
* Hosting de APIs REST.
* Escalabilidad en la nube.
* Integración con Docker.
* Acceso remoto a endpoints y documentación Swagger.

---

### 🔄 Arquitectura de Despliegue

```text
Frontend (Vercel)
        ↓
Consumo mediante Fetch API
        ↓
Backend Spring Boot (Railway)
        ↓
Base de Datos MySQL
```

Esta arquitectura permite separar responsabilidades entre cliente y servidor, facilitando mantenimiento, escalabilidad y despliegue independiente de cada componente.



# 📚 Objetivo del Proyecto

El objetivo principal de este sistema es optimizar y digitalizar los procesos de gestión relacionados con bancos de sangre, permitiendo una administración más organizada, rápida y segura de la información.

Además, el proyecto sirve como práctica de desarrollo full stack utilizando tecnologías modernas del ecosistema Java y aplicaciones web.

---

ADSO - SENA.

---


