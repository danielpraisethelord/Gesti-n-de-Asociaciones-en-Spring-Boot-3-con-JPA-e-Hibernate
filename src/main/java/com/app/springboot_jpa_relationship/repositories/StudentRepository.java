package com.app.springboot_jpa_relationship.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.app.springboot_jpa_relationship.entities.Course;
import com.app.springboot_jpa_relationship.entities.Student;

public interface StudentRepository extends CrudRepository<Student, Long> {

    @Query("select s from Student s left join fetch s.courses where s.id = :id")
    Optional<Student> findOneWithCourse(@Param("id") Long id);

    List<Student> findByCoursesContaining(Course course);
}
