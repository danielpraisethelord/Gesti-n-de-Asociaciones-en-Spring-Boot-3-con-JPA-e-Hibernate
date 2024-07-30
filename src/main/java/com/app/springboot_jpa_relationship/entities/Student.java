package com.app.springboot_jpa_relationship.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.Objects;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String lastname;

    /*
     * la anotación @JoinTable se utiliza para definir la tabla de unión (join
     * table) que se necesita para gestionar una relación ManyToMany entre
     * entidades. Aquí está la explicación de cada parámetro:
     * 
     * name:
     * 
     * Descripción: Especifica el nombre de la tabla de unión en la base de datos.
     * Ejemplo: name = "tbl_students_courses" crea una tabla llamada
     * tbl_students_courses.
     * joinColumns:
     * 
     * Descripción: Define la columna en la tabla de unión que se refiere a la
     * entidad propietaria de la relación (la entidad en la que estás definiendo
     * esta relación ManyToMany).
     * Ejemplo: joinColumns = @JoinColumn(name = "id_student") crea una columna
     * llamada id_student en la tabla de unión que hace referencia a la clave
     * primaria de la entidad Student.
     * inverseJoinColumns:
     * 
     * Descripción: Define la columna en la tabla de unión que se refiere a la
     * entidad opuesta de la relación (la otra entidad en la relación ManyToMany).
     * Ejemplo: inverseJoinColumns = @JoinColumn(name = "id_course") crea una
     * columna llamada id_course en la tabla de unión que hace referencia a la clave
     * primaria de la entidad Course.
     * uniqueConstraints:
     * 
     * Descripción: Define restricciones únicas en la tabla de unión. Esto se
     * utiliza para asegurarse de que no haya duplicados en la combinación de
     * columnas especificadas.
     * Ejemplo: uniqueConstraints = @UniqueConstraint(columnNames = { "id_student",
     * "id_course" }) asegura que la combinación de id_student y id_course sea única
     * en la tabla tbl_students_courses, evitando así que el mismo estudiante esté
     * registrado en el mismo curso más de una vez.
     */
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "tbl_students_courses", joinColumns = @JoinColumn(name = "id_student"), inverseJoinColumns = @JoinColumn(name = "id_course"), uniqueConstraints = @UniqueConstraint(columnNames = {
            "id_student", "id_course" }))
    private Set<Course> courses;

    public Student() {
        this.courses = new HashSet<>();
    }

    public Student(String name, String lastname) {
        this();
        this.name = name;
        this.lastname = lastname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course) {
        this.courses.add(course);
        course.getStudents().add(this);
    }

    public void removeCourse(Course course) {
        this.courses.remove(course);
        course.getStudents().remove(this);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", name='" + getName() + "'" +
                ", lastname='" + getLastname() + "'" +
                ", courses='" + getCourses() + "'" +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Student)) {
            return false;
        }
        Student student = (Student) o;
        return Objects.equals(id, student.id) && Objects.equals(name, student.name)
                && Objects.equals(lastname, student.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastname);
    }

}
