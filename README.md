# Documentación del Proyecto Spring Boot 3 con JPA e Hibernate

## Introducción
Este proyecto es una aplicación de Spring Boot 3 que utiliza JPA (Java Persistence API) e Hibernate para gestionar la persistencia de datos en una base de datos relacional. El proyecto se centra en demostrar las diferentes asociaciones entre entidades (tablas) y cómo se pueden implementar estas relaciones utilizando las anotaciones de JPA. Se cubren los tipos de relaciones ManyToOne, OneToMany, OneToOne y ManyToMany, tanto unidireccionales como bidireccionales, junto con operaciones CRUD (Crear, Leer, Actualizar, Eliminar).

## Estructura del Proyecto
El proyecto tiene la siguiente estructura de archivos y carpetas:

- **src/main/java/com/app/springboot_jpa_relationship**
  - **entities**: Contiene las clases de entidad que representan las tablas en la base de datos.
  - **repositories**: Contiene las interfaces de repositorio que extienden `CrudRepository` para las operaciones CRUD.
  - **SpringbootJpaRelationshipApplication.java**: La clase principal que inicia la aplicación Spring Boot.
- **pom.xml**: Archivo de configuración de Maven.

## Entidades y Relaciones

### Address
Representa la dirección de un cliente.

### Client
Representa a un cliente en el sistema. Tiene una relación OneToOne con `ClientDetails` y una relación ManyToOne con `Address`.

### ClientDetails
Contiene detalles adicionales sobre un cliente y tiene una relación OneToOne con `Client`.

### Course
Representa un curso que puede ser tomado por estudiantes. Tiene una relación ManyToMany con `Student`.

### Invoice
Representa una factura emitida a un cliente.

### Student
Representa un estudiante que puede tomar múltiples cursos. Tiene una relación ManyToMany con `Course`.

## Relaciones entre Entidades
- **Client - ClientDetails**: Relación OneToOne bidireccional. `Client` es la entidad propietaria.
- **Client - Address**: Relación ManyToOne unidireccional.
- **Student - Course**: Relación ManyToMany bidireccional. La tabla intermedia es gestionada mediante `@JoinTable`.

## Anotaciones de JPA Usadas

### @Entity
Marca una clase como una entidad JPA.

### @Table
Especifica el nombre de la tabla en la base de datos que se mapeará a la entidad.

### @Id
Define la clave primaria de una entidad.

### @GeneratedValue
Indica que el valor de la clave primaria será generado automáticamente.

### @ManyToOne
Define una relación ManyToOne. Parámetros importantes:
- **cascade**: Especifica las operaciones en cascada.
- **fetch**: Define el tipo de carga (LAZY o EAGER).

### @OneToMany
Define una relación OneToMany. Parámetros importantes:
- **mappedBy**: Define el lado inverso de la relación.
- **cascade**: Especifica las operaciones en cascada.
- **fetch**: Define el tipo de carga (LAZY o EAGER).
- **orphanRemoval**: Si se establece en true, las entidades huérfanas se eliminarán automáticamente.

### @OneToOne
Define una relación OneToOne. Parámetros importantes:
- **cascade**: Especifica las operaciones en cascada.
- **fetch**: Define el tipo de carga (LAZY o EAGER).
- **mappedBy**: Define el lado inverso de la relación.

### @ManyToMany
Define una relación ManyToMany. Parámetros importantes:
- **mappedBy**: Define el lado inverso de la relación.
- **cascade**: Especifica las operaciones en cascada.
- **fetch**: Define el tipo de carga (LAZY o EAGER).

### @JoinColumn
Especifica la columna de unión en una relación. Parámetros importantes:
- **name**: Nombre de la columna de unión.
- **referencedColumnName**: Nombre de la columna referenciada.

### @JoinTable
Define una tabla intermedia para relaciones ManyToMany. Parámetros importantes:
- **name**: Nombre de la tabla intermedia.
- **joinColumns**: Columnas de unión de la entidad propietaria.
- **inverseJoinColumns**: Columnas de unión de la entidad inversa.

### @Transactional
Indica que el método o clase debe ser ejecutado dentro de una transacción.

### @Query
Permite definir consultas JPQL personalizadas.

### @Modifying
Indica que una consulta @Query realiza una operación de modificación (insert, update, delete).

## Creación de Tablas Automáticamente
Las tablas en la base de datos se crean automáticamente a partir de las entidades definidas en el proyecto. Hibernate analiza las anotaciones y genera el esquema de la base de datos durante el inicio de la aplicación.

## Tablas Intermediarias
En relaciones ManyToMany, Hibernate crea automáticamente una tabla intermedia para gestionar la relación entre las dos entidades. Esta tabla intermedia contiene las claves primarias de ambas tablas relacionadas.

## Funcionalidad de la Aplicación
La clase principal `SpringbootJpaRelationshipApplication.java` inicia la aplicación Spring Boot. Los repositorios proporcionan métodos CRUD para interactuar con la base de datos.

## Repositorios
- **ClientDetailsRepository**
- **ClientRepository**
- **CourseRepository**
- **InvoiceRepository**
- **StudentRepository**

Estos repositorios extienden `CrudRepository` y proporcionan métodos para realizar operaciones CRUD en las entidades correspondientes.


Este proyecto de Spring Boot 3 con JPA e Hibernate proporciona una base sólida para comprender y trabajar con asociaciones entre entidades en una aplicación Java. Las anotaciones de JPA permiten definir claramente las relaciones y gestionar la persistencia de datos de manera eficiente. Las operaciones CRUD y la configuración automática de la base de datos facilitan la creación y gestión de aplicaciones complejas con múltiples relaciones entre entidades.
