# Sistema de Gestión Civil y Electoral - Java (Servlets + JSP)

## Descripción

Aplicación web desarrollada en Java (javax) utilizando arquitectura MVC, Servlets y JSP.

El sistema permite a la Registraduría gestionar de forma centralizada la información civil y electoral de los ciudadanos, reemplazando el uso de hojas de cálculo desactualizadas.

Incluye tres módulos principales:

- Gestión de Ciudadanos  
- Gestión de Documentos Expedidos  
- Consulta Electoral (solo lectura)  

Además, el sistema implementa un enfoque **Multi-DB dinámico**, permitiendo cambiar el motor de base de datos en tiempo de ejecución desde la aplicación sin modificar el código fuente.

Repositorio:  
https://github.com/zorroedward0/Evidencias  

Rama:  
AppRegistraduria  

---

## Tecnologías utilizadas

- Java (JDK 8 o superior - javax)
- Servlets y JSP
- JSTL
- Apache Tomcat 9+
- JDBC
- Bootstrap 5
- Motores de base de datos:
  - PostgreSQL (principal)
  - SQLite
  - H2
  - SQL Server
  - Supabase

---

## Justificación del motor de base de datos

El motor principal seleccionado fue **PostgreSQL**, debido a:

- Facilidad de conexión con JDBC  
- Alto rendimiento en consultas  
- Estabilidad y robustez  
- Excelente manejo de relaciones y JOINs  
- Compatibilidad con soluciones en la nube como Supabase  

---

## Soporte Multi-DB

El sistema permite trabajar con múltiples motores de base de datos configurables:

- PostgreSQL  
- SQLite  
- H2  
- SQL Server  
- Supabase  

### Consideraciones importantes

- Es necesario **crear la base de datos en cada motor** que se desee utilizar  
- Se debe ejecutar el archivo:

CDI sql todas las db.txt

Este archivo contiene:

- Sentencias `DROP`  
- Sentencias `CREATE`  
- Sentencias `INSERT`  

Debe ejecutarse en cada motor para garantizar la misma estructura y datos iniciales.

---

## Configuración de base de datos

El sistema utiliza el archivo:

db.properties

### Motor activo

```properties
db.engine=postgresql

# PostgreSQL (LOCAL - PRINCIPAL)
postgresql.url=jdbc:postgresql://localhost:5432/registraduria_db
postgresql.user=postgres
postgresql.password=123

# SQLite (archivo local)
sqlite.url=jdbc:sqlite:C:/Users/PC_18/Downloads/Adso Mañana/AppMultiDb/identifier.sqlite

# H2 (modo local)
h2.url=jdbc:h2:C:/Users/PC_18/Downloads/Adso Mañana/AppRegistraduriaMunicipal/Registraduria;AUTO_SERVER=TRUE

# SQL Server
sqlserver.url=jdbc:sqlserver://localhost:1433;databaseName=db_registraduria;encrypt=true;trustServerCertificate=true
sqlserver.user=javaConexion
sqlserver.password=Java123*

# Supabase (PostgreSQL en la nube)
supabase.url=jdbc:postgresql://aws-1-us-east-1.pooler.supabase.com:6543/postgres?sslmode=require
supabase.user=postgres.ddmvvjhtufkjcslermhv
supabase.password=supabase123456789*10

Notas importantes

Para forzar el cambio del motor de base de datos inicial:


db.engine=sqlite
```---
Para H2 y SQLite es necesario ajustar las rutas locales según el equipo

Supabase requiere conexión a internet

SQL Server debe estar previamente configurado



---

Selector dinámico de base de datos

El sistema incluye una interfaz web que permite:

Seleccionar el motor de base de datos

Visualizar la base activa en sesión

Validar el estado de conexión

Cambiar de motor sin reiniciar la aplicación



---

Requisitos previos

JDK 8 o superior

Apache Tomcat

IDE (NetBeans, IntelliJ o Eclipse)

Motores de base de datos a utilizar



---

Instalación

1. Clonar el repositorio

git clone https://github.com/zorroedward0/Evidencias.git
cd Evidencias
git checkout AppRegistraduria

2. Crear la base de datos (Cada una de las 5)

Ejemplo en PostgreSQL:

CREATE DATABASE registraduria_db;

3. Ejecutar script SQL

Seleccionar cada consulta segun motor de bases de datos en el archivo:

CDI sql todas las db.txt

4. Configurar conexión

Editar el archivo:

db.properties

Para conexion correcta con SQLLITE y H2


5. Desplegar en Tomcat

Importar el proyecto en el IDE

Configurar servidor Tomcat

Ejecutar la aplicación



---

Ejecutar en el IDE Seleccionado



---

Funcionalidades

Módulo 1 — Gestión de Ciudadanos

Registrar ciudadanos

Listar ciudadanos

Buscar por nombre o documento

Editar información

Eliminar registros


Módulo 2 — Documentos Expedidos

CRUD completo

Tipos de documentos:

Cédula

Tarjeta de identidad

Registro civil

Contraseña


Incluye:

Número de serie único

Fecha de expedición

Fecha de vencimiento

Estado (vigente, vencido, cancelado)


El listado muestra el nombre del ciudadano mediante consultas con JOIN.

Módulo 3 — Consulta Electoral

Consulta por número de documento

Muestra ciudad, zona y mesa


Si el ciudadano no tiene mesa asignada, el sistema muestra un icono referente.

Funciones adicionales:

Listar mesas por zona

Listar zonas por ciudad



---

Arquitectura

El proyecto sigue el patrón MVC:

Model: Entidades

View: JSP + JSTL

Controller: Servlets

DAO: Acceso a datos con JDBC



---

Autor

Eduar Danilo Paipilla Zorro
ADSO - SENA


---

Licencia

Uso académico y educativo.🏢


---
