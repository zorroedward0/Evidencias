# Sistema de Productores Agropecuarios Web - Java (Servlets + JSP)

## Descripción

Aplicación web desarrollada en Java utilizando arquitectura MVC, Servlets, JSP y MySQL.  
Permite la gestión de productores agropecuarios y usuarios con control de acceso por roles (Administrador y Cliente).

Este proyecto se encuentra en la rama:

AppAgropecuario

---

## Estructura del proyecto

El repositorio contiene los siguientes archivos:

📦 Evidencias  
 ┣ 📂 AppProductorAgropecuario final   → Proyecto web (NetBeans / Tomcat)  
 ┣ 📄 inserts completos.sql            → Inserts de datos de prueba  
 ┣ 📄 sql productores_db.sql           → Script de creación de BD  
 ┗ 📄 README.md  

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

### 1. Clonar el repositorio e ir a la rama correspondiente

```bash
git clone https://github.com/zorroedward0/Evidencias.git
cd Evidencias
git checkout AppAgropecuario
```

---

### 2. Configurar la base de datos

1. Crear la base de datos en MySQL:

```sql
CREATE DATABASE productores_agropecuarios_db;
USE productores_agropecuarios_db;
```

2. Ejecutar los scripts SQL incluidos en el proyecto:

Ejecutar primero:

```bash
sql productores_db.sql
```

Luego ejecutar:

```bash
inserts completos.sql
```

---

### 3. Configurar conexión a la base de datos

Ubicar la clase de conexión (`ConexionDb.java`) y modificar:

```java
String url = "jdbc:mysql://localhost:3306/productores_agropecuarios_db";
String user = "root";
String password = "password";
```

---

### 4. Importar el proyecto en el IDE

* Abrir el IDE  
* Importar como proyecto web (Java Web)  
* Import from zip o carpeta  
* Seleccionar la carpeta **AppProductorAgropecuario final**

---

### 5. Configurar servidor Tomcat

* Agregar Apache Tomcat al IDE  
* Desplegar el proyecto en el servidor  

---

## Ejecución

1. Iniciar el servidor Tomcat  
2. Abrir navegador en:

```
http://localhost:8080/AppAgropecuario
```

---

## Credenciales de prueba

Administrador:

* Email: r.almanza@agrored.com  
* Password: 123  

Cliente:

* Email: cmario92@gmail.com  
* Password: 123  

---

## Funcionalidades

* CRUD de Productores  
* CRUD de Usuarios  
* Sistema de Login con sesiones  
* Control de acceso por roles  
* Filtro de autenticación  
* Interfaz con Bootstrap y modales  

---

## Arquitectura

El proyecto sigue el patrón MVC:

* Model: Entidades (Usuario, TipoUsuario, Productor)  
* View: JSP  
* Controller: Servlets  
* DAO: Acceso a datos  

---

## Autor

Eduar Danilo Paipilla Zorro  
ADSO - SENA 🌱