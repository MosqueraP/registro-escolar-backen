package com.registroescolar.backend.repository;

import com.registroescolar.backend.model.Administrativo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministrativoRepository extends JpaRepository<Administrativo, Long> {
    Page<Administrativo> findByDepartamentoContainingIgnoreCase(String departamento, Pageable pageable);
    Page<Administrativo> findByNombreContainingIgnoreCaseOrDepartamentoContainingIgnoreCase(String nombre, String departamento, Pageable pageable);

}
