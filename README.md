# UTEC++

## CS 2031 Desarrollo Basado en Plataforma

### Integrantes:
- Santiago Aldebaran Cama Ardiles
- Gabriel Nicolás Frisancho Gálvez
- Camila Del Rosario Espinoza Cabrera
- Ricardo Reyes Pizzani

---

## Índice
1. [Introducción](#introducción)
2. [Identificación del Problema o Necesidad](#identificación-del-problema-o-necesidad)
3. [Descripción de la Solución](#descripción-de-la-solución)
4. [Modelo de Entidades](#modelo-de-entidades)
5. [Testing y Manejo de Errores](#testing-y-manejo-de-errores)
6. [Medidas de Seguridad Implementadas](#medidas-de-seguridad-implementadas)
7. [Eventos y Asincronía](#eventos-y-asincronía)
8. [GitHub](#github)
9. [Conclusión](#conclusión)
10. [Apéndices](#apéndices)

---

## Introducción

### Contexto
La plataforma UTEC++ surge como respuesta a la necesidad de una herramienta académica integrada que facilite la interacción entre profesores, estudiantes y asesores. UTEC++ se enfoca en simplificar la gestión de tareas, evaluaciones, materiales y asesorías en una única interfaz, mejorando la experiencia de los usuarios dentro del entorno académico de UTEC.

### Objetivos del Proyecto
- *Objetivo General:* Crear una plataforma académica que permita la interacción entre profesores, estudiantes y asesores, gestionando tareas, evaluaciones, materiales y asesorías de forma eficiente.
- *Objetivos Específicos:*
  - Mejorar la administración de evaluaciones y notas.
  - Fomentar la colaboración mediante asesorías y foros de discusión.
  - Optimizar la comunicación a través de mensajería instantánea.
  - Garantizar la seguridad y privacidad de los datos.

---

## Identificación del Problema o Necesidad

### Descripción del Problema
Actualmente, UTEC depende de plataformas externas para la gestión de actividades académicas, lo cual presenta desafíos en cuanto a personalización y control de datos. Esto genera ineficiencias y dificultades para garantizar una experiencia óptima a los usuarios.

### Justificación
Es esencial contar con una plataforma personalizada que cubra las necesidades específicas de la comunidad UTEC, mejorando la experiencia del usuario y garantizando la privacidad y seguridad de la información.

---

## Descripción de la Solución

### Funcionalidades Implementadas
- *Gestión múltiple:* Esta funcionalidad permite a los profesores y administradores publicar de manera eficiente diferentes tipos de contenido académico dentro de cada curso. Esto incluye la subida de materiales de estudio, la creación de tareas con fechas límite, la publicación de evaluaciones y la programación de asesorías. La plataforma organiza todo este contenido de manera estructurada, lo que facilita tanto la visualización como la administración de los recursos del curso en un solo lugar.
- *Sistema de notas:* Se implementó un sistema de calificaciones que permite a los profesores gestionar de forma ordenada las evaluaciones de los estudiantes. Este sistema está diseñado para ofrecer un seguimiento detallado del rendimiento académico a lo largo del curso, permitiendo a los estudiantes consultar sus notas de manera rápida y sencilla.
- *Roles de usuario:* La plataforma cuenta con un esquema de roles de usuario bien definido que otorga distintos niveles de acceso y permisos según el rol asignado. Los profesores pueden gestionar tareas, evaluaciones, materiales, y participar activamente en los foros. Los estudiantes tienen acceso a las tareas y evaluaciones, pueden participar en los foros, y visualizar sus notas. Los asesores, por otro lado, tienen un rol de apoyo y pueden revisar el progreso académico y ofrecer asesorías personalizadas. Esta división de roles garantiza que cada usuario tenga acceso solo a las funcionalidades pertinentes a su perfil, asegurando un entorno académico organizado y seguro.
- *Foros de discusión y anuncios:* La plataforma fomenta la interacción entre los estudiantes y profesores a través de foros moderados de discusión diseñados para facilitar el intercambio de ideas, preguntas y respuestas sobre temas del curso. Además, los profesores pueden hacer anuncios personalizados para mantener a los estudiantes informados sobre cambios importantes en las actividades del curso, como recordatorios de fechas límite o nuevas publicaciones de materiales. Estas notificaciones se integran en la plataforma para que los usuarios reciban alertas en tiempo real, manteniendo una comunicación académica efectiva.

### Tecnologías Utilizadas (revisar)
- *Lenguajes:* JavaScript, HTML
- *Frameworks:* React
- *Bases de Datos:* PostgreSQL
- *APIs externas:* Google Authentication

---

## Modelo de Entidades (falta ordenar el draw.io)

### Diagrama de Entidades
![Diagrama de Entidades](nombre de la foto) Aún falta completar el diagrama

### Descripción de Entidades
- *Usuario:* Esta es una entidad central que almacena la información básica de los usuarios del sistema, ya sean estudiantes, profesores o asesores. Los atributos clave incluyen id_usuario, nombre_usuario, correo, contraseña, y rol, que permite identificar si el usuario es un Profesor, Alumno, o Asesor. Los usuarios también tienen un campo de relación hacia sus roles específicos. Además, se gestiona la autenticación del usuario mediante la combinación de correo y contraseña, y su acceso al sistema está determinado por el rol asignado.
	- *Alumno:* Esta entidad describe a los estudiantes del sistema y contiene atributos como id_alumno, carrera y la relación hacia las asesorías y cursos que están inscritos. Cada alumno puede ser asistido por un asesor, como indica la relación puede ser asesorado, y también lleva cursos registrados, lo que le permite participar en actividades académicas dentro de la plataforma.
	*Profesor:* La entidad Profesor almacena los datos de los profesores, con atributos como id_profesor y departamento, permitiendo la asignación de un profesor a los cursos que dicta.
	- *Asesor:* Esta entidad se vincula con los alumnos y tiene atributos como id_asesor y la relación hacia los alumnos a quienes asesora. Los asesores pueden ofrecer asesorías, como se describe en el sistema, brindando apoyo académico a los alumnos.
- *Curso:* Un curso es una entidad clave que organiza todas las actividades académicas. Cada curso tiene atributos como id_curso, nombre_curso, y créditos, lo que permite definir las características del curso. Un profesor puede dictar el curso, mientras que los alumnos lo llevan y los asesores pueden también ser parte del curso, asistiendo a los alumnos inscritos.
- *Tarea:* Las tareas son actividades asignadas a los estudiantes en un curso. Cada tarea tiene un id_tarea, un título, una descripción, y fechas relevantes como fecha_entrega y la nota_maxima. Estas tareas están vinculadas al curso y a las entregas de los estudiantes.
- Entrega: Las entregas están asociadas a las tareas y representan el trabajo que un alumno presenta para ser evaluado. Cada entrega tiene un id_entrega, y está conectada tanto con el curso como con la tarea correspondiente.
- Material: Esta entidad contiene los materiales didácticos de cada curso. Cada material está vinculado a un curso y contiene los recursos que los alumnos deben consultar.
- *Evaluación:* Son las pruebas o exámenes que forman parte del sistema de evaluación del curso. Se encuentran asociadas a las notas que los estudiantes y cada uno de sus cursos. reciben como parte de su rendimiento académico.
- *Notas:* Esta entidad registra las calificaciones que los alumnos obtienen en las evaluaciones, lo que les permite monitorear su progreso académico a lo largo del curso.
- *Asesoría:* Las asesorías son sesiones personalizadas en las que los asesores ayudan a los alumnos con sus inquietudes académicas. Están asociadas a los cursos y alumnos que reciben asesoría.
- *Anuncios:* Los anuncios son comunicados que los profesores publican para mantener informados a los alumnos acerca de fechas importantes, cambios en las actividades o recordatorios generales dentro del curso.
- *Chat y Grupos:* Los chats y grupos son funcionalidades que permiten la interacción entre los usuarios del sistema. Los estudiantes pueden participar en grupos de discusión o foros para resolver dudas y colaborar entre ellos.

---

## Testing y Manejo de Errores 

### Niveles de Testing Realizados (falta)
- *Pruebas Unitarias:* Validación de las funciones principales de gestión de evaluaciones y mensajería. 
- *Pruebas de Integración:* Aseguramiento de la correcta interacción entre los módulos de notas y foros.

### Resultados (falta)
- Se corrigieron problemas de sincronización en las evaluaciones y errores en la interfaz de los foros.

### Manejo de Errores
El manejo de errores en la plataforma UTEC++ se realiza utilizando un controlador de excepciones global basado en Spring Boot, el cual intercepta las excepciones más comunes para devolver respuestas apropiadas al cliente.

Excepciones Globales Implementadas:
- *ResourceNotFoundException:* Esta excepción es lanzada cuando un recurso solicitado, como una tarea, evaluación o material, no se encuentra en el sistema. Se maneja devolviendo un estado 404 "Not Found" junto con un mensaje que describe el recurso faltante.
- *UserAlreadyExistsException:* Esta excepción es utilizada cuando se intenta registrar un usuario que ya existe en el sistema. Se responde con un estado 409 "Conflict" y un mensaje explicativo para el cliente.

Este manejo centralizado de excepciones garantiza que los errores comunes sean tratados de forma consistente y adecuada en toda la plataforma. La utilización de anotaciones como @RestControllerAdvice permite gestionar las respuestas HTTP sin replicar el código en cada controlador, haciendo el sistema más mantenible y robusto.

---

## Medidas de Seguridad Implementadas

### Seguridad de Datos
- *Cifrado de contraseñas:* Las contraseñas de los usuarios se almacenan de manera segura utilizando un mecanismo de cifrado mediante PasswordEncoder antes de ser guardadas en la base de datos. Esto asegura que las contraseñas nunca se almacenen en texto plano y que solo puedan ser verificadas mediante comparación cifrada.
- *Autenticación basada en JWT (JSON Web Tokens):* Para gestionar la autenticación de usuarios. Este método asegura que solo los usuarios autenticados puedan acceder a los recursos protegidos.
	- *Autenticación al inicio de sesión:* Al autenticarse, el usuario proporciona sus credenciales (email y contraseña). Si las credenciales son válidas, el servidor genera un token JWT que es devuelto al cliente. El token contiene información codificada que identifica al usuario y sus roles.
	- *Registro de usuarios:* Durante el registro, se verifica si el usuario ya existe. Si no es así, se registra un nuevo usuario con su rol correspondiente (ADMIN, TEACHER, ASSISTANT, STUDENT) y se genera un token JWT para su autenticación.
- *Control de Acceso Basado en Roles:* Cada usuario tiene un rol definido (ADMIN, TEACHER, ASSISTANT, STUDENT) que determina los permisos y accesos dentro de la plataforma. Se implementa un control estricto para garantizar que solo los usuarios con los permisos adecuados puedan realizar acciones críticas, como gestionar evaluaciones o asignar notas.
	- Roles Implementados:
		- ADMIN: Acceso completo a todos los recursos del sistema.
		- TEACHER: Acceso a los cursos y evaluaciones que enseña.
		- ASSISTANT: Acceso limitado a la gestión de cursos, colaborando con los profesores.
		- STUDENT: Acceso a las tareas, evaluaciones, y material del curso en el que está inscrito.
	- *Funciones de autorización:* Se implementaron verificaciones como isAdmin(), isTeacher(), isStudent(), entre otras, para controlar el acceso según el rol del usuario autenticado. También se asegura que solo los propietarios de recursos o los administradores puedan realizar operaciones críticas.

### Prevención de Vulnerabilidades
- *Protección contra Inyección SQL:* Todas las consultas a la base de datos se realizan mediante consultas parametrizadas y seguras, lo que previene la inyección de código malicioso en las interacciones con la base de datos.
- *Validación de Entradas:* Se implementan validaciones rigurosas en todos los endpoints para asegurar que los datos ingresados sean válidos y seguros. Esto incluye la validación de correos electrónicos, contraseñas y roles, garantizando que la aplicación solo procese datos correctos.
- *Autorización basada en JWT:* Todos los endpoints críticos están protegidos y solo pueden ser accedidos mediante un token JWT válido. Esto evita accesos no autorizados y asegura que las solicitudes provienen de usuarios autenticados.

---

## Eventos y Asincronía

El sistema de mensajería y notificaciones de UTEC++ se basa en eventos asíncronos para garantizar una comunicación rápida y en tiempo real, especialmente en las notificaciones de anuncios y en la mensajería instantánea entre estudiantes y profesores.

- *SignInEvent y SignInEventListener:* Durante el proceso de registro de un nuevo usuario en la plataforma, se dispara un evento llamado SignInEvent que contiene la información clave del nuevo usuario, como su email, nombre y apellido. Este evento es capturado por el SignInEventListener, que de manera asíncrona envía un correo electrónico de bienvenida al nuevo usuario. El uso de eventos permite que el sistema continúe con otras operaciones sin esperar que se complete el envío del correo, mejorando así la eficiencia y experiencia del usuario.
- *WherebyMeetingEvent y WherebyMeetingEventListener:* En los casos en que se crea una reunión en la plataforma (como una sesión de mentoría o clase), se dispara un evento llamado WherebyMeetingEvent. Este evento contiene la información de la sesión, como el nombre de la sección, el enlace de la sala de reunión y el correo electrónico del destinatario. El WherebyMeetingEventListener procesa este evento de manera asíncrona y envía una notificación por correo electrónico al usuario invitado con el enlace para unirse a la reunión.

Ventajas de la Programación Asíncrona:

- *Mejora de Rendimiento:* Los eventos se manejan de manera asíncrona, lo que permite al sistema realizar otras tareas sin necesidad de esperar a que se completen acciones como el envío de correos electrónicos o notificaciones, mejorando el rendimiento y la capacidad de respuesta de la plataforma.

- *Desacoplamiento:* La lógica relacionada con el registro de usuarios o la creación de reuniones está desacoplada del proceso de notificación, lo que facilita el mantenimiento y la extensión de la funcionalidad sin afectar al flujo principal de las aplicaciones.

---

## GitHub (explicar más)

### Uso de GitHub Projects
- Se crearon issues por cada funcionalidad y corrección de errores.
- Se gestionaron deadlines para cumplir con las entregas semanales.

### GitHub Actions
- Se implementó un flujo de CI/CD para la integración continua y el despliegue automático.

---

## Conclusión

### Logros del Proyecto
- El proyecto UTEC++ ha alcanzado varios hitos importantes, destacando la creación de una plataforma robusta y funcional que facilita la gestión integral de tareas, evaluaciones, y materiales educativos. Además, se incorporaron foros de discusión, proporcionando un espacio colaborativo para estudiantes y profesores, lo cual optimiza la interacción dentro del ámbito académico.

- Otro aspecto clave es la implementación de medidas de seguridad avanzadas, como la autenticación basada en JWT y la gestión de permisos según los roles de usuario (estudiantes, docentes, y administradores). Estas medidas aseguran que la información sensible de los usuarios esté protegida, garantizando la integridad de los datos y la prevención de vulnerabilidades comunes en entornos web.
  
### Aprendizajes Clave
- *Administración de bases de datos:* Se adquirió una comprensión profunda en la estructuración y optimización de bases de datos, asegurando que la plataforma pudiera manejar múltiples roles de usuario y mantener un registro seguro y eficiente de tareas, evaluaciones y materiales.
- *Seguridad en aplicaciones web:* Se mejoró significativamente la capacidad de implementar técnicas de seguridad como el cifrado de contraseñas, la protección contra inyecciones SQL, y el uso de autenticación mediante tokens JWT, asegurando un acceso controlado a la plataforma.
- *Eventos y asincronías:* El uso de eventos asíncronos para notificaciones y la gestión de eventos del sistema permitió mejorar la escalabilidad y el rendimiento del sistema, con una arquitectura que asegura que procesos como el envío de correos electrónicos no bloqueen el flujo principal de la aplicación.

### Trabajo Futuro (revisar)
- Implementación en el Frontend: Además, está previsto mejorar el frontend de la plataforma con funcionalidades adicionales que mejoren la interacción y la experiencia de usuario. Entre estas, se contempla implementar notificaciones en tiempo real, facilitar la navegación entre secciones, y mejorar la accesibilidad y el diseño responsivo, para que los usuarios puedan acceder cómodamente desde dispositivos móviles.

---

## Apéndices

### Licencia
Este proyecto está bajo la licencia MIT.

### Referencias
- Documentación oficial de React
