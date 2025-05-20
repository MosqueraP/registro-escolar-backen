package com.registroescolar.backend.service.impl;

import com.registroescolar.backend.dto.PersonaDTO;
import com.registroescolar.backend.dto.ProfesorDTO;
import com.registroescolar.backend.exception.OperacionInvalidaException;
import com.registroescolar.backend.exception.OperacionNoPermitidaException;
import com.registroescolar.backend.exception.RecursoNoEncontradoException;
import com.registroescolar.backend.model.Persona;
import com.registroescolar.backend.model.PersonaBase;
import com.registroescolar.backend.repository.PersonRepository;
import com.registroescolar.backend.repository.ProfesorRepository;
import com.registroescolar.backend.service.interfaces.PersonaService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonaServiceImpl implements PersonaService {

    private final PersonRepository personaRepository;
    private final ModelMapper modelMapper;

    public PersonaServiceImpl(PersonRepository personaRepository, ModelMapper modelMapper) {
        this.personaRepository = personaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PersonaDTO crearPersona(PersonaDTO dto) {
        if (personaRepository.existsByEmail(dto.getEmail())) {
            throw new OperacionInvalidaException("No se puede registrar: el correo ya está en uso.");
        }

        PersonaBase persona = modelMapper.map(dto, PersonaBase.class);
        persona = personaRepository.save(persona);
        return modelMapper.map(persona, PersonaDTO.class);
    }

    @Override
    public List<PersonaDTO> obtenerTodas() {
        return personaRepository.findAll()
                .stream()
                .map(p -> modelMapper.map(p, PersonaDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public PersonaDTO obtenerPorId(Long id) {
        Persona persona = personaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Persona con ID " + id + " no encontrada."));
        return modelMapper.map(persona, PersonaDTO.class);
    }


    @Override
    public PersonaDTO actualizarPersona(Long id, PersonaDTO dto) {
        Persona persona = personaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se puede actualizar: persona con ID " + id + " no existe."));

        persona.setNombre(dto.getNombre());
        persona.setApellido(dto.getApellido());
        persona.setEmail(dto.getEmail());
        persona.setTelefono(dto.getTelefono());
        persona.setFechaNacimiento(dto.getFechaNacimiento());

        personaRepository.save(persona);
        return modelMapper.map(persona, PersonaDTO.class);
    }

    @Override
    public void eliminarPersona(Long id) {
        if (!personaRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("No se puede eliminar: persona con ID " + id + " no existe.");
        }
        try {
            personaRepository.deleteById(id);
        } catch (Exception e) {
            throw new OperacionNoPermitidaException("No se puede eliminar la persona: existen relaciones asociadas.");
        }
    }


    @Override
    public Page<PersonaDTO> listarPaginado(Pageable pageable) {
        return personaRepository.findAll(pageable)
                .map(p -> modelMapper.map(p, PersonaDTO.class));
    }



    @Override
    public Page<PersonaDTO> buscarPorNombre(String nombre, Pageable pageable) {
        return personaRepository.findByNombreContainingIgnoreCase(nombre, pageable)
                .map(persona -> modelMapper.map(persona, PersonaDTO.class));
    }



}




