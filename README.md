# RestApi_Shopall
RestApi para tienda ShopAll

Objetivo: Crear una API RESTful utilizando Spring Boot y JPA para la tienda shopAll (diagrama creado en el ejercicio 1).

1. Configuración inicial del proyecto
1. Crear un nuevo proyecto de Spring Boot utilizando un ide de preferencia.
2. Configurar las dependencias necesarias, como Spring Web, Spring Data JPA y una
base de datos relacional (MySQL, SQL, Postgress, etc)
3. Establecer la configuración de la base de datos y otras configuraciones relevantes en
el archivo de configuración de la aplicación.
2. Creación de entidades y repositorios.
1. Define las entidades con los campos necesarios.
2. Crear repositorios JPA para las entidades que extienda de JpaRepository.
3. Anota la entidad y el repositorio con las anotaciones adecuadas de JPA y Spring.
3. Inyección de dependencias y configuraciones de beans.
1. Crear los servicios que implemente la lógica de negocio para administrar la tienda.
2. Inyecta los repositorios de las entidades en los servicios utilizando la anotación de
inyección de dependencia adecuada.
3. Anota el servicio y el repositorio con las anotaciones de Spring correspondientes para
que se configuren como beans de Spring.
4. Controladores y notaciones de Spring.
1. Crear controladores que maneje las solicitudes HTTP relacionadas con las tareas de
la tienda.
2. Define métodos en el controlador para manejar las operaciones CRUD (crear, leer,
actualizar y eliminar) de las tareas utilizando las anotaciones adecuadas de spring.
3. Utiliza las notaciones adecuadas de spring para mapear las rutas URL y manejar la
serialización/deserialización de los objetos JSON.
5. Pruebas y verificación.
1. Utiliza una herramienta como Postman o curl para enviar solicitudes HTTP a la API y
verificar que las operaciones CRUD funcionen correctamente.


## Pruebas:
- Escribe pruebas unitarias utilizando JUnit y Mockito para verificar el comportamiento
correcto de la API en diferentes escenarios, como el registro exitoso de un producto, las
respuestas de error ante datos inválidos y la detección de emails duplicados.
- Asegúrate de incluir casos de prueba que cubran las validaciones de datos y el manejo de
excepciones.
- Enfócate en el manejo de excepciones, en futuras actividades implementarás pruebas para
el resto del sistema.

## NOTAS:

- La base de datos en mysql se llama shopall.
- En la RestApi puede realizar un CRUD en usuarios la cual esta relacionada con la tabla direcciones.
- En la RestApi puede realizar un CRUD en Productos la cual esta relacionada con la tabla comentarios.
- La RestApi esta documentada con swager.
- La RestApi esta testeda con Postman.
