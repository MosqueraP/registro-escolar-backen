package com.registroescolar.backend.repository;

import com.registroescolar.backend.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    boolean existsByEmail(String email);
    Optional<Person> findByEmail(String email);
}
