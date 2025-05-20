package com.registroescolar.backend.service.impl;

import com.registroescolar.backend.dto.ProfesorAsignacionDTO;
import com.registroescolar.backend.dto.ProfesorDTO;
import com.registroescolar.backend.exception.BadRequestException;
import com.registroescolar.backend.exception.NotFoundException;
import com.registroescolar.backend.exception.RecursoNoEncontradoException;
import com.registroescolar.backend.model.Persona;
import com.registroescolar.backend.model.Profesor;
import com.registroescolar.backend.repository.PersonRepository;
import com.registroescolar.backend.repository.ProfesorRepository;
import com.registroescolar.backend.service.interfaces.ProfesorService;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfesorServiceImpl implements ProfesorService {

    private final ProfesorRepository profesorRepository;
    private final ModelMapper modelMapper;

    public ProfesorServiceImpl(ProfesorRepository profesorRepository, ModelMapper modelMapper) {
        this.profesorRepository = profesorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProfesorDTO guardar(ProfesorDTO dto) {
        Profesor profesor = modelMapper.map(dto, Profesor.class);
        profesor = profesorRepository.save(profesor);
        return modelMapper.map(profesor, ProfesorDTO.class);
    }

    @Override
    public ProfesorDTO actualizar(Long id, ProfesorDTO dto) {
        Profesor profesor = profesorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));

        dto.setIdPersona(profesor.getIdPersona()); // evitar cambiar el ID
        modelMapper.map(dto, profesor);
        profesor = profesorRepository.save(profesor);
        return modelMapper.map(profesor, ProfesorDTO.class);
    }

    @Override
    public List<ProfesorDTO> listar() {
        return profesorRepository.findAll().stream()
                .map(p -> modelMapper.map(p, ProfesorDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public ProfesorDTO obtenerPorId(Long id) {
        Profesor profesor = profesorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Profesor con ID " + id + " no encontrado."));
        return modelMapper.map(profesor, ProfesorDTO.class);
    }

//    @Override
//    public void eliminar(Long id) {
//        profesorRepository.deleteById(id);
//    }
    @Override
    public void eliminar(Long id) {
        if (!profesorRepository.existsById(id)) {
            throw new NotFoundException("No se puede eliminar: profesor con ID " + id + " no existe.");
        }
        try {
            profesorRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new BadRequestException("No se puede eliminar: el profesor tiene relaciones activas (por ejemplo, cursos asignados).");
        }
    }


    @Override
    public Page<ProfesorDTO> listarPaginado(Pageable pageable) {
        return profesorRepository.findAll(pageable)
                .map(profesor -> modelMapper.map(profesor, ProfesorDTO.class));
    }

    @Override
    public Page<ProfesorDTO> buscarPorNombre(String nombre, int page, int size) {
        return profesorRepository
                .findByNombreContainingIgnoreCase(nombre, PageRequest.of(page, size))
                .map(p -> modelMapper.map(p, ProfesorDTO.class));
    }

}