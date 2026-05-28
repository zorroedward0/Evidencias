# 🚗 AppEstacionamiento- Parqueadero Boyacá

AppEstacionamiento es un sistema web desarrollado para la administración y control de un parqueadero. El proyecto fue realizado como parte del proceso formativo ADSO del SENA, aplicando conceptos de desarrollo backend, frontend, bases de datos y arquitectura por capas.

El sistema permite registrar vehículos, controlar entradas y salidas, consultar historial de movimientos y gestionar la información relacionada con el funcionamiento del parqueadero.

---

# 📌 Objetivo del proyecto

Desarrollar una solución tecnológica que facilite el control y administración de un parqueadero mediante un sistema centralizado capaz de:

- Registrar vehículos.
- Gestionar ingresos y salidas.
- Controlar estados de parqueo.
- Consultar historial de movimientos.
- Organizar información de manera eficiente.
- Automatizar procesos manuales.

---

# 🧩 Funcionalidades principales

## Gestión de vehículos

El sistema permite:

- Registrar vehículos nuevos.
- Consultar información de vehículos.
- Editar datos registrados.
- Eliminar registros.
- Clasificar vehículos según su tipo.

---

## Gestión de entradas y salidas

El proyecto incluye un módulo encargado de:

- Registrar el ingreso de vehículos.
- Registrar la salida de vehículos.
- Controlar vehículos activos dentro del parqueadero.
- Generar historial de movimientos.
- Filtrar registros por fecha y tipo de vehículo.

---

## Historial del parqueadero

El sistema cuenta con una sección de historial donde se pueden visualizar:

- Vehículos ingresados.
- Vehículos retirados.
- Fechas de ingreso y salida.
- Tipo de vehículo.
- Información general de los registros.

---

# 🛠️ Tecnologías utilizadas

## Backend

- Java
- Java Servlets
- Maven
- JDBC
- Apache Tomcat
- MySQL

---

## Frontend

- PHP
- HTML5
- CSS3
- JavaScript

---

# 🧱 Arquitectura del proyecto

El sistema fue desarrollado utilizando arquitectura por capas para mantener una mejor organización del código.

## DAO

Encargada de la comunicación con la base de datos.

## Model

Representa las entidades principales del sistema.

## DTO

Permite transferir datos entre diferentes capas.

## Servlet

Maneja las solicitudes HTTP y expone la API REST.

## Util

Contiene clases auxiliares y configuraciones generales.

---

# 📂 Estructura general del proyecto

```bash
AppEstacionamiento/
│
├── Backend/
│   ├── dao/
│   ├── dto/
│   ├── model/
│   ├── servlet/
│   └── util/
│
├── Frontend/
│   ├── css/
│   ├── js/
│   ├── pages/
├── └── assets/

```

---

# 🔌 API REST

El backend expone diferentes endpoints para gestionar la información del parqueadero.

## Funciones principales de la API

- Registro de vehículos.
- Consulta de vehículos.
- Registro de entradas.
- Registro de salidas.
- Consulta de historial.
- Filtrado de registros.

---

# 💻 Interfaz del sistema

La interfaz fue desarrollada con un enfoque sencillo y funcional.

El frontend permite:

- Visualizar información del parqueadero.
- Registrar movimientos.
- Consultar historial.
- Navegar entre diferentes módulos del sistema.
- Consumir datos desde la API REST.

---

# 📊 Características técnicas

- Arquitectura organizada por capas.
- Separación entre frontend y backend.
- Integración con base de datos MySQL.
- Manejo de peticiones HTTP.
- Sistema CRUD completo.
- Historial dinámico de movimientos.
- Filtrado de información.
- Gestión de estados de parqueo.

---

# 🎯 Finalidad académica

Este proyecto fue desarrollado con fines académicos para fortalecer conocimientos en:

- Desarrollo web.
- Programación Java.
- APIs REST.
- Bases de datos.
- Arquitectura de software.
- Integración frontend y backend.
- Gestión de proyectos ADSO.

---

**ADSO - Análisis y Desarrollo de Software**  
SENA Regional Boyacá



