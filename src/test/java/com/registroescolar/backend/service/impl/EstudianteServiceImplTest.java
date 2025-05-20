package com.registroescolar.backend.service.impl;


import com.registroescolar.backend.dto.EstudianteAsignacionDTO;
import com.registroescolar.backend.dto.EstudianteDTO;
import com.registroescolar.backend.model.Estudiante;
import com.registroescolar.backend.repository.EstudianteRepository;
import com.registroescolar.backend.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
class EstudianteServiceImplTest {

    @Mock
    private EstudianteRepository estudianteRepository;

    @Mock
    private PersonRepository personRepository;

    private ModelMapper modelMapper;

    @InjectMocks
    private EstudianteServiceImpl estudianteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        modelMapper = new ModelMapper();
        estudianteService = new EstudianteServiceImpl(estudianteRepository, personRepository, modelMapper);
    }

    @Test
    void guardar_DeberiaGuardarYDevolverEstudianteDTO() {
        // Arrange
        EstudianteDTO dto = new EstudianteDTO();
        dto.setNombre("Juan");
        dto.setApellido("Gómez");
        dto.setEmail("juan@correo.com");
        dto.setTelefono("123456789");
        dto.setFechaNacimiento(LocalDate.of(2000, 1, 1));
        dto.setNumeroMatricula("MAT123");
        dto.setGrado("10A");

        Estudiante entidad = modelMapper.map(dto, Estudiante.class);
        entidad.setIdPersona(1L); // Simulamos que fue guardado

        when(estudianteRepository.existsByNumeroMatricula("MAT123")).thenReturn(false);
        when(estudianteRepository.save(any(Estudiante.class))).thenReturn(entidad);

        // Act
        EstudianteDTO resultado = estudianteService.guardar(dto);

        // Assert
        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
        assertEquals("MAT123", resultado.getNumeroMatricula());
    }

    @Test
    void listar_DeberiaDevolverListaDeEstudiantes() {
        List<Estudiante> lista = List.of(new Estudiante(), new Estudiante());
        when(estudianteRepository.findAll()).thenReturn(lista);

        List<EstudianteAsignacionDTO> resultado = estudianteService.listar();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
    }

}