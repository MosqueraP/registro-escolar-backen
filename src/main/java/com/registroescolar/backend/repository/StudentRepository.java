package com.registroescolar.backend.repository;

import com.registroescolar.backend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // creamos los repositorios para poder crear los métodos CRUD
    Optional<Student> findByEmail(String email);
    Optional<Student> findByEnrollmentNumber(String enrollmentNumber);
    boolean existsByEnrollmentNumber(String enrollmentNumber);
    boolean existsByEmail(String email);


}
