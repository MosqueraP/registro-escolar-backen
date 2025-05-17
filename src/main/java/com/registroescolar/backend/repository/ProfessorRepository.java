package com.registroescolar.backend.repository;

import com.registroescolar.backend.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {

}
