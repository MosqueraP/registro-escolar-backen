package com.registroescolar.backend.service.impl;

import com.registroescolar.backend.dto.EstudianteAsignacionDTO;
import com.registroescolar.backend.dto.EstudianteDTO;
import com.registroescolar.backend.dto.EstudianteRegistroDTO;
import com.registroescolar.backend.exception.OperacionInvalidaException;
import com.registroescolar.backend.exception.RecursoNoEncontradoException;
import com.registroescolar.backend.model.Estudiante;
import com.registroescolar.backend.model.Persona;
import com.registroescolar.backend.repository.EstudianteRepository;
import com.registroescolar.backend.repository.PersonRepository;
import com.registroescolar.backend.service.interfaces.EstudianteService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstudianteServiceImpl implements EstudianteService {

    private final EstudianteRepository estudianteRepository;
    private final PersonRepository personaRepository;
    private final ModelMapper modelMapper;

    @ResponseStatus(HttpStatus.CONFLICT)
    public class DuplicadoException extends RuntimeException {
        public DuplicadoException(String mensaje) {
            super(mensaje);
        }
    }


    public EstudianteServiceImpl(EstudianteRepository estudianteRepository,
                                 PersonRepository personaRepository,
                                 ModelMapper modelMapper) {
        this.estudianteRepository = estudianteRepository;
        this.personaRepository = personaRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public EstudianteDTO guardar(EstudianteDTO dto) {
        if (estudianteRepository.existsByNumeroMatricula(dto.getNumeroMatricula())) {
            throw new OperacionInvalidaException("Número de matrícula ya existe.");
        }

        Estudiante estudiante = modelMapper.map(dto, Estudiante.class);
        estudiante = estudianteRepository.save(estudiante);
        return modelMapper.map(estudiante, EstudianteDTO.class);
    }



    @Override
    public List<EstudianteAsignacionDTO> listar() {
        return estudianteRepository.findAll()
                .stream()
                .map(e -> modelMapper.map(e, EstudianteAsignacionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public EstudianteAsignacionDTO obtenerPorId(Long id) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Estudiante con ID " + id + " no encontrado."));
        return modelMapper.map(estudiante, EstudianteAsignacionDTO.class);
    }

    @Override
    public void eliminar(Long id) {
        if (!estudianteRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Estudiante con ID " + id + " no encontrado.");
        }

        estudianteRepository.deleteById(id);
    }




    @Override
    public EstudianteDTO actualizar(Long id, EstudianteDTO dto) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Estudiante con ID " + id + " no encontrado."));

        dto.setNumeroMatricula(estudiante.getNumeroMatricula());
        estudiante.setNombre(dto.getNombre());
        estudiante.setApellido(dto.getApellido());
        estudiante.setFechaNacimiento(dto.getFechaNacimiento());
        estudiante.setEmail(dto.getEmail());
        estudiante.setTelefono(dto.getTelefono());
        estudiante.setGrado(dto.getGrado());

        estudiante = estudianteRepository.save(estudiante);
        return modelMapper.map(estudiante, EstudianteDTO.class);
    }

    // paginación
    @Override
    public Page<EstudianteDTO> listarPaginado(Pageable pageable) {
        return estudianteRepository.findAll(pageable)
                .map(est -> modelMapper.map(est, EstudianteDTO.class));
    }

    @Override
    public Page<EstudianteDTO> buscarPorNombre(String nombre, Pageable pageable) {
        return estudianteRepository.findByNombreContainingIgnoreCase(nombre, pageable)
                .map(estudiante -> modelMapper.map(estudiante, EstudianteDTO.class));
    }


}
