# Documentación de la API de GestionBiblioteca

## Introducción

Bienvenido a la documentación oficial de la API de GestionBiblioteca. Esta API proporciona acceso a diversas funciones de la aplicación, permitiendo a los desarrolladores integrar y aprovechar sus características en sus propias aplicaciones.

## URL Base

La URL base para todas las solicitudes a la API es:

http://localhost:8080/api/<entidad_correspondiente>


## Endpoints Disponibles

### 1. Obtener Información de un Bibliotecario

**Endpoint:** `/bibliotecario`

**Método:** `GET`

**Descripción:** Obtiene una lista de todos los bibliotecarios.

**Parámetros de consulta:**
- Ninguno

**Ejemplo de solicitud:**
`curl -X GET http://localhost:8080/api/bibliotecario -H "accept: application/json"`


## 2. Crear un Nuevo Bibliotecario

**Endpoint:** `/bibliotecario`

**Método:** `POST`

**Descripción:** Crea un nuevo bibliotecario en la biblioteca.

**Parámetros de solicitud:**
- `nombre` (cadena): Nombre del bibliotecario.
- `email` (cadena): Correo electrónico del bibliotecario.

**Ejemplo de solicitud:**
`curl -X POST http://localhost:8080/api/bibliotecario -H "accept: application/json" -d "nombre=NombreBibliotecario&email=bibliotecario@biblioteca.com"`

## 3. Obtener Información de un Lector

**Endpoint:** `/lector`

**Método:** `GET`

**Descripción:** Obtiene una lista de todos los lectores.

**Parámetros de consulta:**
- Ninguno

**Ejemplo de solicitud:**
`curl -X GET http://localhost:8080/api/lector -H "accept: application/json"`

## 4. Crear un Nuevo Lector

**Endpoint:** `/lector`

**Método:** `POST`

**Descripción:** Crea un nuevo lector en la biblioteca.

**Parámetros de solicitud:**
- `nombre` (cadena): Nombre del lector.
- `direccion` (cadena): Dirección del lector.

**Ejemplo de solicitud:**
`curl -X POST http://localhost:8080/api/lector -H "accept: application/json" -d "nombre=NombreLector&direccion=DireccionLector"`

## 5. Obtener Información de un Libro

**Endpoint:** `/libro`

**Método:** `GET`

**Descripción:** Obtiene una lista de todos los libros.

**Parámetros de consulta:**
- Ninguno

**Ejemplo de solicitud:**
`curl -X GET http://localhost:8080/api/libro -H "accept: application/json"`

## 6. Crear un Nuevo Libro

**Endpoint:** `/libro`

**Método:** `POST`

**Descripción:** Crea un nuevo libro en la biblioteca.

**Parámetros de solicitud:**
- `titulo` (cadena): Título del libro.
- `autor` (cadena): Autor del libro.

**Ejemplo de solicitud:**
`curl -X POST http://localhost:8080/api/libro -H "accept: application/json" -d "titulo=TituloLibro&autor=AutorLibro"`

## 7. Obtener Información de un Préstamo

**Endpoint:** `/prestamo`

**Método:** `GET`

**Descripción:** Obtiene una lista de todos los préstamos.

**Parámetros de consulta:**
- Ninguno

**Ejemplo de solicitud:**
`curl -X GET http://localhost:8080/api/prestamo -H "accept: application/json"`

## 8. Crear un Nuevo Préstamo

**Endpoint:** `/prestamo`

**Método:** `POST`

**Descripción:** Crea un nuevo préstamo en la biblioteca.

**Parámetros de solicitud:**
- `idLector` (entero): ID del lector que realiza el préstamo.
- `idLibro` (entero): ID del libro que se presta.

**Ejemplo de solicitud:**
`curl -X POST http://localhost:8080/api/prestamo -H "accept: application/json" -d "idLector=1&idLibro=1"`

## 9. Buscar un Bibliotecario por ID

**Endpoint:** `/bibliotecario/{id}`

**Método:** `GET`

**Descripción:** Obtiene información detallada sobre un bibliotecario específico.

**Parámetros de consulta:**
- `id` (entero): ID del bibliotecario.

**Ejemplo de solicitud:**
`curl -X GET http://localhost:8080/api/bibliotecario/1 -H "accept: application/json"`

## 10. Buscar un Lector por ID

**Endpoint:** `/lector/{id}`

**Método:** `GET`

**Descripción:** Obtiene información detallada sobre un lector específico.

**Parámetros de consulta:**
- `id` (entero): ID del lector.

**Ejemplo de solicitud:**
`curl -X GET http://localhost:8080/api/lector/1 -H "accept: application/json"`

## 11. Buscar un Préstamo por ID

**Endpoint:** `/prestamo/{id}`

**Método:** `GET`

**Descripción:** Obtiene información detallada sobre un préstamo específico.

**Parámetros de consulta:**
- `id` (entero): ID del préstamo.

**Ejemplo de solicitud:**
`curl -X GET http://localhost:8080/api/prestamo/1 -H "accept: application/json"`

## 12. Buscar un Libro por ISBN

**Endpoint:** `/libro/{isbn}`

**Método:** `GET`

**Descripción:** Obtiene información detallada sobre un libro específico mediante su ISBN.

**Parámetros de consulta:**
- `isbn` (cadena): ISBN del libro.

**Ejemplo de solicitud:**
`curl -X GET http://localhost:8080/api/libro/978-3-16-148410-0 -H "accept: application/json"`

## Respuestas de la API

Las respuestas de la API estarán en formato JSON e incluirán un campo `status` que indicará el estado de la solicitud (éxito o error) y un campo `data` que contendrá los datos solicitados o un mensaje de error.

**Ejemplo de respuesta exitosa:**
`
{
  "status": "success",
  "data": {
    "id": 1,
    "nombre": "NombreBibliotecario",
    "email": "bibliotecario@biblioteca.com"
  }
}
`   
**Ejemplo de respuesta de error:**
`
{
  "status": "error",
  "data": "No se pudo completar la solicitud. Verifique los parámetros proporcionados."
}
`
## Conclusiones

Esta documentación proporciona una visión general de los endpoints disponibles en la API de GestionBiblioteca. Se recomienda revisar detenidamente los parámetros de cada solicitud y comprender las respuestas de la API para garantizar un uso efectivo en tus proyectos relacionados con la gestión de bibliotecas.

Si tienes alguna pregunta o encuentras problemas, no dudes en ponerte en contacto con nuestro equipo de soporte en support@gestionbiblioteca.com.

¡Agradecemos tu elección de utilizar la API de GestionBiblioteca! Esperamos que encuentres esta documentación útil y que disfrutes aprovechando las capacidades de nuestra aplicación en tus propios proyectos!
