package com.registroescolar.backend.service.interfaces;

import com.registroescolar.backend.dto.EstudianteAsignacionDTO;
import com.registroescolar.backend.dto.EstudianteDTO;
import com.registroescolar.backend.dto.EstudianteRegistroDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EstudianteService {

    EstudianteDTO guardar(EstudianteDTO dto);

    EstudianteDTO actualizar(Long id, EstudianteDTO dto);

    List<EstudianteAsignacionDTO> listar();

    EstudianteAsignacionDTO obtenerPorId(Long id);

    void eliminar(Long id);

    Page<EstudianteDTO> listarPaginado(Pageable pageable);

    Page<EstudianteDTO> buscarPorNombre(String nombre, Pageable pageable);





}
