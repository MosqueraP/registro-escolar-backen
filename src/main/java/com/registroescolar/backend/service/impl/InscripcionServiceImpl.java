package com.registroescolar.backend.service.impl;

import com.registroescolar.backend.dto.InscripcionDTO;
import com.registroescolar.backend.exception.BadRequestException;
import com.registroescolar.backend.exception.NotFoundException;
import com.registroescolar.backend.model.Curso;
import com.registroescolar.backend.model.Estudiante;
import com.registroescolar.backend.model.Inscripcion;
import com.registroescolar.backend.repository.CursoRepository;
import com.registroescolar.backend.repository.EstudianteRepository;
import com.registroescolar.backend.repository.InscripcionRepository;
import com.registroescolar.backend.service.interfaces.InscripcionService;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InscripcionServiceImpl implements InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final EstudianteRepository estudianteRepository;
    private final CursoRepository cursoRepository;
    private final ModelMapper modelMapper;

    public InscripcionServiceImpl(InscripcionRepository inscripcionRepository,
                                  EstudianteRepository estudianteRepository,
                                  CursoRepository cursoRepository,
                                  ModelMapper modelMapper) {
        this.inscripcionRepository = inscripcionRepository;
        this.estudianteRepository = estudianteRepository;
        this.cursoRepository = cursoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public InscripcionDTO guardar(InscripcionDTO dto) {
        Estudiante estudiante = estudianteRepository.findById(dto.getIdEstudiante())
                .orElseThrow(() -> new NotFoundException("Estudiante con ID " + dto.getIdEstudiante() + " no encontrado"));
        Curso curso = cursoRepository.findById(dto.getIdCurso())
                .orElseThrow(() -> new NotFoundException("Curso con ID " + dto.getIdCurso() + " no encontrado, crea primero uno"));

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setEstudiante(estudiante);
        inscripcion.setCurso(curso);
        inscripcion.setFechaInscripcion(dto.getFechaInscripcion());

        try {
            inscripcion = inscripcionRepository.save(inscripcion);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Este estudiante ya está inscrito en el curso.");
        }

        return modelMapper.map(inscripcion, InscripcionDTO.class);
    }

    @Override
    public InscripcionDTO actualizar(Long id, InscripcionDTO dto) {
        Inscripcion inscripcion = inscripcionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));

        Estudiante estudiante = estudianteRepository.findById(dto.getIdEstudiante())
                .orElseThrow(() -> new NotFoundException("Estudiante con ID " + dto.getIdEstudiante() + " no encontrado"));

        Curso curso = cursoRepository.findById(dto.getIdCurso())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        inscripcion.setEstudiante(estudiante);
        inscripcion.setCurso(curso);
        inscripcion.setFechaInscripcion(dto.getFechaInscripcion());

        inscripcion = inscripcionRepository.save(inscripcion);
        return modelMapper.map(inscripcion, InscripcionDTO.class);
    }

    @Override
    public InscripcionDTO obtenerPorId(Long id) {
        Inscripcion inscripcion = inscripcionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));
        return modelMapper.map(inscripcion, InscripcionDTO.class);
    }

    @Override
    public List<InscripcionDTO> listar() {
        return inscripcionRepository.findAll()
                .stream()
                .map(i -> modelMapper.map(i, InscripcionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long id) {
        inscripcionRepository.deleteById(id);
    }

    @Override
    public Page<InscripcionDTO> listarPaginado(Pageable pageable) {
        return inscripcionRepository.findAll(pageable)
                .map(insc -> modelMapper.map(insc, InscripcionDTO.class));
    }

}