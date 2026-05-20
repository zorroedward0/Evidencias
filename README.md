# Sistema de Gestión de Vacunación — Spring Boot

Proyecto desarrollado como parte del proceso formativo ADSO – SENA CIMM, enfocado en la transición desde aplicaciones Java basadas en Servlets hacia el ecosistema Spring Boot utilizando arquitectura en capas, Spring Data JPA y Thymeleaf.

El sistema fue diseñado como una aplicación web orientada a la gestión básica de pacientes y vacunas dentro de un entorno de vacunación, implementando operaciones CRUD completas, persistencia con MySQL y generación dinámica de vistas HTML.

---

# Objetivo del Proyecto

El propósito principal del proyecto fue comprender el funcionamiento de Spring Boot desde su configuración inicial hasta el desarrollo de una aplicación funcional conectada a base de datos.

Durante el desarrollo se trabajó el uso de:

- Spring Boot
- Spring Web
- Spring Data JPA
- Thymeleaf
- Hibernate
- MySQL
- Maven
- Arquitectura MVC
- Inyección de dependencias
- Internacionalización (i18n)

---

# Funcionalidades Implementadas

## Gestión de Pacientes
- Registro de pacientes.
- Edición de información.
- Eliminación de registros.
- Listado dinámico utilizando Thymeleaf.
- Persistencia automática mediante JPA/Hibernate.

## Gestión de Vacunas
- CRUD completo de vacunas.
- Validación de vacunas vencidas.
- Visualización de estado mediante badges dinámicos.
- Gestión de lotes y laboratorios.

## Arquitectura Spring
- Separación por capas:
  - Controller
  - Service
  - Repository
  - Model
- Uso de @Entity, @Service, @Controller y JpaRepository.
- Inyección de dependencias por constructor.
- Manejo automático de transacciones con @Transactional.

## Interfaz Web
- Vistas desarrolladas con Thymeleaf.
- Formularios dinámicos enlazados mediante th:field.
- Navbar reutilizable usando fragments.
- Estilos personalizados con colores corporativos.

## Internacionalización
- Soporte para español e inglés.
- Uso de messages.properties y messages_en.properties.
- Cambio dinámico de idioma mediante parámetro lang.

---

# Tecnologías Utilizadas

- Java 17
- Spring Boot 3
- Spring Web
- Spring Data JPA
- Thymeleaf
- Hibernate
- MySQL
- Maven
- Bootstrap 5

---

# Objetivos Técnicos Alcanzados

El proyecto permitió comprender conceptos fundamentales del ecosistema Spring como:

- Configuración automática de aplicaciones.
- Persistencia ORM con Hibernate.
- Generación automática de consultas con Spring Data JPA.
- Desarrollo de aplicaciones MVC modernas.
- Manejo de dependencias mediante Maven.
- Uso de Tomcat embebido.
- Separación adecuada de responsabilidades.
- Desarrollo más mantenible y escalable frente a proyectos basados únicamente en Servlets.

---

Adso 2026
