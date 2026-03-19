# ♻️ ReWear - API REST de Economía Circular (Moda Sostenible)

## 1. 🎯 Visión General del Proyecto

ReWear es una plataforma Backend diseñada para transformar la industria de la moda promoviendo la **economía circular**. Nuestra misión es extender el ciclo de vida de las prendas de vestir mediante un sistema de intercambio seguro, transparente y eficiente.

Este servicio gestiona el ciclo de vida completo de una prenda: desde su publicación y categorización hasta su intercambio final entre usuarios. Actúa como el motor de confianza que valida la propiedad de los recursos y asegura que las transacciones ocurran bajo el consentimiento mutuo de ambas partes.

***

## 2. 🛡️ Características Críticas de Integridad y Seguridad

Para garantizar un entorno de intercambio justo y prevenir el uso indebido de la plataforma, hemos implementado las siguientes salvaguardas:

### A. Control de Propiedad (Ownership Validation)
El sistema impone una validación estricta de propiedad en cada operación sensible.
* **Seguridad de Prenda:** Solo el propietario de una prenda o un usuario con rol `ADMIN` puede modificar sus detalles o cambiar su estado de visibilidad (`active`).
* **Integridad de Oferta:** Al iniciar un intercambio, el sistema verifica que la prenda ofrecida pertenezca realmente al solicitante, impidiendo que se comprometan recursos ajenos.

### B. Ciclo de Vida Garantizado (Exchange Workflow)
El proceso de intercambio sigue un flujo de estados inmutable para evitar inconsistencias:
1. **PENDING:** El intercambio es propuesto. Las prendas permanecen `AVAILABLE`.
2. **ACCEPTED:** El dueño de la prenda solicitada acepta el trato. Ambas prendas pasan automáticamente a estado `RESERVED`.
3. **COMPLETED:** Requiere la **doble confirmación** (tanto del solicitante como del dueño). Solo entonces las prendas se marcan como `EXCHANGED`.

### C. Sistema de Reputación y Confianza (Rating System)
Para fomentar la confianza en la comunidad, el sistema incluye un motor de calificaciones post-intercambio:
* **Condición de Cierre:** Solo se permiten reseñas una vez que el intercambio alcanza el estado `COMPLETED`.
* **Cálculo Dinámico:** El sistema recalcula automáticamente el promedio de calificación (`rating`) y el total de valoraciones (`totalRatings`) del usuario reseñado en tiempo real.
* **Unicidad:** Cada participante solo puede calificar una vez por intercambio, evitando la manipulación de la reputación.

### D. Seguridad Stateless y Refresh Tokens
Implementamos **JWT (JSON Web Tokens)** para una autenticación sin estado, reforzada con un mecanismo de **Refresh Tokens**. Esto permite sesiones prolongadas y seguras sin necesidad de re-autenticar constantemente al usuario, manteniendo la integridad de la sesión mediante rotación de claves.

***

## 3. ⚙️ Arquitectura Técnica y DevOps

| Componente | Detalle |
| :--- | :--- |
| **Stack Principal** | Spring Boot 3.3.4, Java 21, Spring Data JPA. |
| **Seguridad** | Spring Security + JWT (io.jsonwebtoken 0.13.0). |
| **Base de Datos** | **SQL Server** (Producción/Dev) | **H2** (Pruebas). |
| **Migraciones** | **Flyway** (Gestión de versiones del esquema SQL). |
| **Mapeo de Datos** | **MapStruct** (Conversión eficiente entre Entidades y DTOs). |
| **Documentación** | **OpenAPI 3 / Swagger UI** (Documentación viva de la API). |
| **Validación** | Jakarta Bean Validation (Validación estricta de payloads). |

***

## 4. 🔒 Contrato de Roles y Seguridad (RBAC)

El sistema utiliza un control de acceso basado en roles (`USER`, `ADMIN`) y seguridad a nivel de método (`@PreAuthorize`) para proteger los recursos.

| Módulo | Endpoint (Ruta) | Funcionalidad | Roles Requeridos | Notas de Seguridad |
| :--- | :--- | :--- | :--- | :--- |
| **Auth** | `/auth/**` | Registro, Login, Refresh | `PermitAll` | Acceso público para gestión de sesión. |
| **Prendas** | `GET /api/wears` | Catálogo de prendas | `isAuthenticated()` | Permite filtrado por talla, género, estado, etc. |
| **Prendas** | `POST /api/wears` | Publicar Prenda | `hasRole('USER')` | El owner se asigna automáticamente al Principal. |
| **Prendas** | `PUT /api/wears/{id}` | Editar Prenda | `Owner / ADMIN` | Validación cruzada de ID de usuario. |
| **Intercambios** | `POST /api/exchanges` | Solicitar Intercambio | `hasRole('USER')` | Valida que las prendas no pertenezcan al mismo usuario. |
| **Intercambios** | `PATCH /.../accept` | Aceptar Trato | `Owner de prenda` | Solo el receptor de la oferta puede aceptar. |
| **Intercambios** | `PATCH /.../confirm`| Confirmar Entrega | `Participantes` | Requiere confirmación de ambos para finalizar. |
| **Reseñas** | `POST /api/reviews/{id}/review` | Calificar Usuario | `Participantes` | Solo tras `COMPLETED`. Actualiza reputación. |
| **Usuarios** | `/api/users/**` | Perfil y Ajustes | `Owner / ADMIN` | Privacidad de datos personales. |

***

## 5. 🏗️ Estructura de Flyway (Evolución de Datos)

Nuestras migraciones en `src/main/resources/db/migration` aseguran la consistencia del esquema en todos los entornos:

| Versión | Tabla | Propósito |
| :--- | :--- | :--- |
| `V1` | `users` | Entidad base de usuarios con roles y estatus de cuenta. |
| `V2` | `wears` | Almacenamiento de prendas con atributos de moda (talla, material, marca). |
| `V3` | `exchanges` | Gestión de transacciones, estados de intercambio y flags de confirmación. |
| `V4` | `reviews` | Sistema de reputación para calificar la experiencia post-intercambio. |

***

## 🚀 Inicio Rápido

1. **Variables de Entorno:** Configura tu conexión a SQL Server en `application.properties`.
2. **Build:** Ejecuta `./mvnw clean install`.
3. **Run:** Lanza la aplicación con `./mvnw spring-boot:run`.
4. **Docs:** Explora los endpoints en `http://localhost:8080/swagger-ui.html`.
