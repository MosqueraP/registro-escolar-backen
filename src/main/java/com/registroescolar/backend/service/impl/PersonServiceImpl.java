package com.registroescolar.backend.service.impl;

import com.registroescolar.backend.dto.PersonDTO;
import com.registroescolar.backend.exception.DuplicateResourceException;
import com.registroescolar.backend.exception.NotFoundException;
import com.registroescolar.backend.model.Person;
import com.registroescolar.backend.repository.PersonRepository;
import com.registroescolar.backend.service.interfaces.PersonService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<PersonDTO> getAll() {
        return personRepository.findAll().stream()
                .map(p -> modelMapper.map(p, PersonDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PersonDTO getById(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Person not found with id: " + id));
        return modelMapper.map(person, PersonDTO.class);
    }

    @Override
    public PersonDTO create(PersonDTO dto) {
        Person person = modelMapper.map(dto, Person.class);
        person = personRepository.save(person);
        return modelMapper.map(person, PersonDTO.class);
    }

    @Override
    public PersonDTO update(Long id, PersonDTO dto) {
        Person existing = personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Person not found with id: " + id));

        // Validar si el email está siendo cambiado y ya existe en otra persona
        if (!existing.getEmail().equals(dto.getEmail()) &&
                personRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Email already registered");
        }

        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setEmail(dto.getEmail());
        existing.setPhone(dto.getPhone());
        existing.setBirthDate(dto.getBirthDate());

        personRepository.save(existing);
        return modelMapper.map(existing, PersonDTO.class);
    }

    @Override
    public void delete(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Person not found with id: " + id));
        personRepository.delete(person);
    }
}