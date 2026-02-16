# ♻️ ReWear - API REST de Economía Circular

> **Stack Tecnológico:** Java 21 | Spring Boot 3 | Spring Security (JWT) | SQL Server | Flyway | OpenAPI (Swagger)

ReWear es una plataforma Backend diseñada para facilitar el intercambio, donación y venta de ropa de segunda mano, promoviendo la economía circular. Construida con una arquitectura limpia (N-Capas), esta API REST prioriza la seguridad, la mantenibilidad y las buenas prácticas de desarrollo.

---

## 🛠️ Arquitectura y Decisiones Técnicas

El proyecto se estructuró siguiendo el patrón de diseño **MVC (Model-View-Controller)** y una separación estricta de responsabilidades (Controladores, Servicios, Repositorios).

### 1. Seguridad Stateless con JWT
Para garantizar transacciones seguras entre los usuarios, se implementó un sistema de autenticación y autorización basado en **JSON Web Tokens (JWT)** junto con Spring Security. 
* Los endpoints sensibles (como publicar una prenda o concretar un intercambio) requieren un token válido.
* Se gestionan roles de usuario (Ej. `USER`, `ADMIN`) para restringir el acceso a paneles administrativos.

### 2. Mapeo Eficiente de Datos (DTOs)
Para evitar la sobreexposición de entidades de la base de datos y optimizar el payload de las respuestas HTTP, se implementó **MapStruct**. Esto asegura que el cliente solo reciba la información estrictamente necesaria.

### 3. Migraciones de Base de Datos
La evolución del esquema de la base de datos (SQL Server) se controla estrictamente mediante **Flyway**. Esto garantiza que todos los entornos (desarrollo, pruebas, producción) mantengan la consistencia de los datos y facilita el despliegue continuo.

### 4. Documentación Interactiva
Toda la API está documentada utilizando **OpenAPI / Swagger**, permitiendo a los desarrolladores Frontend o a los evaluadores probar los endpoints fácilmente sin necesidad de configuraciones adicionales.

---

## 🚀 Guía de Instalación Local

1. Clonar el repositorio: `git clone https://github.com/Jbyanx/rewear.git`
2. Configurar las variables de entorno para SQL Server en `application.properties`.
3. Ejecutar el proyecto (Flyway creará las tablas automáticamente).
4. Acceder a Swagger UI: `http://localhost:8080/swagger-ui.html`
