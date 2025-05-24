package com.registroescolar.backend.service.impl;

import com.registroescolar.backend.dto.CursoAsignacionDTO;
import com.registroescolar.backend.dto.CursoDTO;
import com.registroescolar.backend.exception.RecursoNoEncontradoException;
import com.registroescolar.backend.model.Curso;
import com.registroescolar.backend.model.Profesor;
import com.registroescolar.backend.repository.CursoRepository;
import com.registroescolar.backend.repository.ProfesorRepository;
import com.registroescolar.backend.service.interfaces.CursoService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CursoServiceImpl implements CursoService {

    private final CursoRepository cursoRepository;
    private final ProfesorRepository profesorRepository;
    private final ModelMapper modelMapper;

    public CursoServiceImpl(CursoRepository cursoRepository,
                            ProfesorRepository profesorRepository,
                            ModelMapper modelMapper) {
        this.cursoRepository = cursoRepository;
        this.profesorRepository = profesorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CursoDTO guardar(CursoDTO dto) {
        Curso curso = modelMapper.map(dto, Curso.class);
        Profesor profesor = profesorRepository.findById(dto.getIdProfesor())
                .orElseThrow(() -> new RecursoNoEncontradoException("Profesor con ID " + dto.getIdProfesor() + " no encontrado"));
        curso.setProfesor(profesor);
        curso = cursoRepository.save(curso);
        return modelMapper.map(curso, CursoDTO.class);
    }

    @Override
    public CursoDTO actualizar(Long id, CursoDTO dto) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Curso con ID " + id + " no encontrado"));

        Profesor profesor = profesorRepository.findById(dto.getIdProfesor())
                .orElseThrow(() -> new RecursoNoEncontradoException("Profesor con ID " + dto.getIdProfesor() + " no encontrado"));

        curso.setNombre(dto.getNombre());
        curso.setDescripcion(dto.getDescripcion());
        curso.setCreditos(dto.getCreditos());
        curso.setProfesor(profesor);

        curso = cursoRepository.save(curso);
        return modelMapper.map(curso, CursoDTO.class);
    }

    @Override
    public List<CursoDTO> listar() {
        return cursoRepository.findAll().stream()
                .map(c -> modelMapper.map(c, CursoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CursoDTO obtenerPorId(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Curso con ID " + id + " no encontrado"));
        return modelMapper.map(curso, CursoDTO.class);
    }

    @Override
    public void eliminar(Long id) {
        if (!cursoRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("No se puede eliminar: curso con ID " + id + " no existe.");
        }
        cursoRepository.deleteById(id);
    }

    @Override
    public Page<CursoDTO> listarPaginado(Pageable pageable) {
        return cursoRepository.findAll(pageable)
                .map(curso -> modelMapper.map(curso, CursoDTO.class));
    }

    @Override
    public Page<CursoDTO> buscarPorNombre(String nombre, Pageable pageable) {
        return cursoRepository.findByNombreContainingIgnoreCase(nombre, pageable)
                .map(curso -> modelMapper.map(curso, CursoDTO.class));
    }

    @Override
    public List<CursoAsignacionDTO> listarConProfesor() {
        return cursoRepository.findAll().stream()
                .map(c -> {
                    CursoAsignacionDTO dto = new CursoAsignacionDTO();
                    dto.setIdCurso(c.getIdCurso());
                    dto.setNombre(c.getNombre());
                    dto.setDescripcion(c.getDescripcion());
                    dto.setCreditos(c.getCreditos());
                    dto.setIdProfesor(c.getProfesor().getId());
                    dto.setNombreProfesor(c.getProfesor().getNombre());
                    dto.setApellidoProfesor(c.getProfesor().getApellido());
                    return dto;
                })
                .collect(Collectors.toList());
    }



}