package com.registroescolar.backend.service.impl;

import com.registroescolar.backend.dto.PersonaDTO;
import com.registroescolar.backend.model.PersonaBase;
import com.registroescolar.backend.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PersonaServiceImplTest {

    @Mock
    private PersonRepository personaRepository;

    private ModelMapper modelMapper;

    @InjectMocks
    private PersonaServiceImpl personaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        modelMapper = new ModelMapper();
        personaService = new PersonaServiceImpl(personaRepository, modelMapper);
    }

    @Test
    void crearPersona_DeberiaGuardarYDevolverPersonaDTO() {
        // Arrange
        PersonaDTO dto = new PersonaDTO();
        dto.setNombre("Carlos");
        dto.setApellido("Gómez");
        dto.setEmail("carlos@correo.com");
        dto.setTelefono("123456789");
        dto.setFechaNacimiento(java.time.LocalDate.of(1990, 1, 1));

        PersonaBase entidad = modelMapper.map(dto, PersonaBase.class);
        entidad.setIdPersona(1L); // simular ID generado

        when(personaRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(personaRepository.save(any(PersonaBase.class))).thenReturn(entidad);

        // Act
        PersonaDTO resultado = personaService.crearPersona(dto);

        // Assert
        assertNotNull(resultado);
        assertEquals("Carlos", resultado.getNombre());
        assertEquals("carlos@correo.com", resultado.getEmail());
        verify(personaRepository).save(any(PersonaBase.class));
    }

}