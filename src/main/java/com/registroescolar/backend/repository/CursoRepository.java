package com.registroescolar.backend.repository;

import com.registroescolar.backend.model.Curso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    Page<Curso> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);

}
