package com.registroescolar.backend.repository;

import com.registroescolar.backend.model.Administrative;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministrativeRepository extends JpaRepository<Administrative, Long> {
}
