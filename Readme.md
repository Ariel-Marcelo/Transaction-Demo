# 🏦 Sistema de Transacciones Bancarias (API REST)

Este proyecto es una solución backend para un sistema financiero que gestiona **Clientes**, **Cuentas** y **Movimientos** bancarios. Fue desarrollado utilizando **Java 17** y **Spring Boot 3**, implementando principios de **Arquitectura Limpia**, patrones de diseño y buenas prácticas de desarrollo de software.

## 🛠️ Stack Tecnológico

* **Lenguaje:** Java 17 (OpenJDK)
* **Framework:** Spring Boot 3.2.x
* **Base de Datos:** PostgreSQL 15
* **Contenerización:** Docker & Docker Compose
* **Mapeo de Objetos:** MapStruct
* **Reducción de Código:** Lombok
* **Testing:** JUnit 5, Mockito & H2 Database (In-Memory)
* **Documentación:** SpringDoc OpenAPI (Swagger)

---

## 🚀 Ejecución con Docker (Recomendado)

La forma más rápida de levantar el entorno completo (Base de datos + Microservicio + PgAdmin) sin configuraciones locales.

### Prerrequisitos
* Docker y Docker Compose instalados.

### Pasos
1.  Clona el repositorio y ubícate en la raíz del proyecto.
2.  Ejecuta el siguiente comando:

```bash
docker-compose up --build

## Ejecución con Maven

```bash
mvn spring-boot:run

```bash
mvn test