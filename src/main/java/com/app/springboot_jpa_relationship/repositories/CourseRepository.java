package com.app.springboot_jpa_relationship.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.app.springboot_jpa_relationship.entities.Course;

import jakarta.transaction.Transactional;

public interface CourseRepository extends CrudRepository<Course, Long> {
    Optional<Course> findByName(String name);

    /*
     * 1. @Modifying
     * La anotación @Modifying se utiliza en Spring Data JPA para indicar que una
     * consulta anotada con @Query realiza una operación de modificación en la base
     * de datos, como UPDATE, DELETE o INSERT. Cuando usas una consulta que modifica
     * los datos, debes anotarla con @Modifying para que Spring Data JPA sepa que
     * debe ejecutar esta consulta como una operación de modificación en lugar de
     * una operación de consulta.
     * 
     * Cuándo usar @Modifying:
     * Cuando tienes una consulta @Query que realiza una operación de modificación
     * de datos en la base de datos.
     * Por ejemplo, cuando quieres eliminar o actualizar registros en la base de
     * datos utilizando JPQL o consultas nativas.
     * 
     * 2. MEMBER OF en JPQL
     * MEMBER OF es una operación de JPQL (Java Persistence Query Language) que se
     * utiliza para verificar si un elemento es miembro de una colección. En el
     * contexto de tu consulta, MEMBER OF se utiliza para verificar si un Course es
     * miembro de la colección courses de Student.
     * 
     * Cuándo usar MEMBER OF:
     * Cuando necesitas comprobar si un elemento está presente en una colección de
     * elementos en JPQL.
     * 
     * En esta consulta, :course MEMBER OF s.courses comprueba si el Course dado es
     * miembro de la colección courses del Student.
     */
    @Modifying
    @Query("DELETE FROM Student s WHERE :course MEMBER OF s.courses")
    void removeCourseFromStudents(@Param("course") Course course);

    /*
     * 3. Métodos default en interfaces
     * En Java 8 y versiones posteriores, puedes definir métodos con
     * implementaciones por defecto (default) en interfaces. Esto permite agregar
     * métodos nuevos a las interfaces sin romper las clases que ya las implementan.
     * Un método default proporciona una implementación predeterminada que las
     * clases que implementan la interfaz pueden usar o sobrescribir.
     * 
     * Cuándo usar métodos default:
     * Cuando necesitas proporcionar una implementación predeterminada para un
     * método en una interfaz.
     * Cuando quieres añadir nuevos métodos a una interfaz existente sin obligar a
     * todas las clases que la implementan a proporcionar una implementación.
     */
    @Modifying
    @Transactional
    default void deleteCourseAndRelationships(Course course) {
        removeCourseFromStudents(course);
        delete(course);
    }

    @Query("select c from Course c left join fetch c.students where c.id = :id")
    Optional<Course> findOneWithStudents(@Param("id") Long id);
}
