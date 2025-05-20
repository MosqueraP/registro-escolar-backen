package com.registroescolar.backend.service.interfaces;

import com.registroescolar.backend.dto.PersonaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PersonaService {
    PersonaDTO crearPersona(PersonaDTO personaDTO);
    List<PersonaDTO> obtenerTodas();
    PersonaDTO obtenerPorId(Long id);
    PersonaDTO actualizarPersona(Long id, PersonaDTO personaDTO);
    void eliminarPersona(Long id);
    Page<PersonaDTO> listarPaginado(Pageable pageable);

    Page<PersonaDTO> buscarPorNombre(String nombre, Pageable pageable);







}