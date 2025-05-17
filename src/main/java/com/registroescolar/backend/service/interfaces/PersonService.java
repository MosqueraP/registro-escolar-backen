package com.registroescolar.backend.service.interfaces;

import com.registroescolar.backend.dto.PersonDTO;

import java.util.List;

public interface PersonService {
    List<PersonDTO> getAll();
    PersonDTO getById(Long id);
    PersonDTO create(PersonDTO dto);
    PersonDTO update(Long id, PersonDTO dto);
    void delete(Long id);
}
