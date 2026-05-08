# 🏥 Sistema de Gestión de Salud – SaludBoyacá 🏥

Sistema web desarrollado en Jakarta EE (Servlets + JSP) para la administración de procesos de atención médica: gestión de pacientes, citas médicas, horarios, usuarios y reportes en PDF.

---

# 🔗 Enlaces del proyecto

- [Repositorio Evidencias AppSaludBoyaca](https://github.com/zorroedward0/Evidencias/tree/AppSaludBoyaca)

- [Imagen Docker (versión v6)](https://hub.docker.com/repository/docker/4edward4/appsaludboyaca/tags/v6/sha256-c5252a024498dc726e979ee1ab7aa80a7c3ccfe4fcbc73d31a81b9e5ecb5fbaa)

- [Carpeta de Google Drive – Video Explicativo](https://drive.google.com/drive/folders/1wTLKCcO7MzEAiW8vR_025I3ku3qbWoCB)

---

# 📌 Descripción del proyecto

Aplicación web orientada a instituciones de salud que permite gestionar el flujo de atención médica mediante una interfaz moderna basada en Bootstrap.

## El sistema permite

- Autenticación y control de roles
- Gestión de pacientes
- Gestión de citas médicas
- Gestión de horarios médicos
- Gestión de usuarios
- Generación de reportes PDF
- Control de sesión y seguridad con filtros
- Internacionalización (i18n)

---

# 🏗 Arquitectura utilizada

## MVC (Modelo – Vista – Controlador)

| Capa | Tecnologías |
|------|-------------|
| Vista (View) | JSP + JSTL + Bootstrap |
| Controlador (Controller) | Servlets |
| Modelo (Model) | POJOs + DAO + Base de datos |

---

# 🛠 Tecnologías usadas

- Jakarta EE 10 (Servlet 6.0)
- JSP + JSTL
- Bootstrap 5
- FontAwesome
- HTML5
- CSS3
- JavaScript
- Filtros Servlet (`AuthFilter` & `LocaleFilter`)
- Generación de PDF

---

# 🔒 Seguridad del sistema

## Rutas protegidas

- `/dashboard`
- `/citas`
- `/horarios`
- `/pacientes`
- `/registros`
- `/usuarios`

Acceso permitido únicamente con sesión activa.

---

# 🌐 Internacionalización

- Idioma por defecto: Español (`es`)
- Resource Bundle: `messages.properties`

---

# 👥 Roles del sistema

| Rol | Permisos |
|------|-----------|
| Administrador | Gestión completa |
| Médico | Ver citas y generar reportes |
| Enfermero | Consulta limitada |
| Recepcionista | Gestión de pacientes y citas |

---

# 🔑 Credenciales de prueba

## 👨‍⚕️ Médico

- Usuario: `cpedraza`
- Contraseña: `admin123`
- Rol: `MEDICO`

## 👩‍⚕️ Enfermero

- Usuario: `msuarez`
- Contraseña: `enfermero1`
- Rol: `ENFERMERO`

## 🧑‍💼 Recepcionista

- Usuario: `jbaez`
- Contraseña: `recep123`
- Rol: `RECEPCIONISTA`

---

# 📅 Gestión de Citas

El módulo principal permite:

- Crear citas
- Editar citas
- Cambiar estados:
  - `PROGRAMADA`
  - `CONFIRMADA`
  - `ATENDIDA`
  - `CANCELADA`
- Generar PDF por médico
- Generar PDF por paciente

---

# 🧾 Reportes PDF

- Reporte de citas por médico
- Reporte de citas por paciente

---

# 🎨 Interfaz de usuario

- Sidebar colapsable
- Dashboard moderno
- Tablas interactivas
- Diseño responsivo

## Colores corporativos

| Tipo | Color |
|------|--------|
| Primario | `#0E6655` |
| Secundario | `#117864` |
| Acento | `#1ABC9C` |

---

# ⚙️ Configuración del servidor

## Página inicial

```txt
/login
Timeout de sesión
30 minutos
Errores globales
404 → /error.jsp
500 → /error.jsp
🐳 Ejecución con Docker
1️⃣ Descargar imagen
docker pull 4edward4/appsaludboyaca:v6
2️⃣ Ejecutar contenedor
docker run -p 8080:8080 4edward4/appsaludboyaca:v6
3️⃣ Abrir en navegador
http://localhost:8080/SistemaVacunacion
▶️ Ejecución manual (sin Docker)
Requisitos
JDK 17+
Apache Tomcat 10+
MySQL o PostgreSQL
Pasos
git clone <repo>
Importar en IDE
Configurar base de datos
Ejecutar en Tomcat
Abrir en navegador
http://localhost:8080/SistemaVacunacion
📂 Estructura del proyecto
src/
│
├── controller/
├── model/
├── dao/
├── util/
├── webapp/
│   ├── WEB-INF/web.xml
│   ├── jsp/
│   └── resources/
🏥 Sistema de Gestión de Atención Médica – SaludBoyacá
