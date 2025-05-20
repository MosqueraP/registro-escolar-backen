package com.registroescolar.backend.repository;

import com.registroescolar.backend.model.Profesor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
    Page<Profesor> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);

}
