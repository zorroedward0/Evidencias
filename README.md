# Sistema de Biblioteca Web - Java (Servlets + JSP)

## Descripción

Aplicación web desarrollada en Java utilizando arquitectura MVC, Servlets, JSP y MySQL.
Permite la gestión de libros, usuarios y préstamos con control de acceso por roles (Administrador y Cliente).

---

## Tecnologías utilizadas

* Java (JDK 8 o superior)
* Servlets y JSP
* Apache Tomcat 9+
* MySQL
* JDBC
* Bootstrap (Frontend)

---

## Requisitos previos

Antes de ejecutar el proyecto debes tener instalado:

* JDK 8 o superior
* Apache Tomcat
* MySQL WorkBench
* IDE (NetBeans)

---

## Instalación

### 1. Clonar el repositorio e ir a rama correspondiente

```bash
git clone https://github.com/zorroedward0/Evidencias.git
cd Evidencias
git checkout AppBliblioteca
```

---

### 2. Configurar la base de datos

1. Crear la base de datos en MySQL:

```sql
CREATE DATABASE dbbiblioteca;
```

2. Ejecutar el script SQL incluido en el proyecto
   (archivo `.sql` con tablas y estructura).

   Ejecutar Script "Consultas Prueba AppBiblioteca.sql"

---

### 3. Configurar conexión a la base de datos

Ubicar la clase de conexión ( `ConexionDb.java`) y modificar:

```java
String url = "jdbc:mysql://localhost:3306/dbbiblioteca";
String user = "root";
String password = "password";
```

---

### 4. Importar el proyecto en el IDE

* Abrir el IDE
* Importar como proyecto web (Java Web) 
* Import from zip 
---

### 5. Configurar servidor Tomcat

* Agregar Apache Tomcat al IDE
* Desplegar el proyecto en el servidor

---

## Ejecución

1. Iniciar el servidor Tomcat
2. Abrir navegador en:

```
http://localhost:8080/AppBiblioteca
```

---

## Credenciales de prueba

Administrador:

* Email: [carlos@gmail.com](mailto:admin@admin.com)
* Password: 123

Cliente:

* Email: [ana@mail.com](mailto:cliente@cliente.com)
* Password: 123

---

## Funcionalidades

* CRUD de Libros
* CRUD de Usuarios
* CRUD de Préstamos
* Sistema de Login con sesiones
* Control de acceso por roles
* Filtro de autenticación
* Interfaz con Bootstrap y modales

---

## Arquitectura

El proyecto sigue el patrón MVC:

* Model: Entidades (Libro, Usuario, Prestamo)
* View: JSP
* Controller: Servlets
* DAO: Acceso a datos

---

## Autor

Eduar Danilo Paipilla Zorro
ADSO - SENA
