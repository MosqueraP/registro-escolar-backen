package com.registroescolar.backend.repository;

import com.registroescolar.backend.model.Estudiante;
import com.registroescolar.backend.model.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    boolean existsByNumeroMatricula(String numeroMatricula); //
    Page<Estudiante> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);



}
