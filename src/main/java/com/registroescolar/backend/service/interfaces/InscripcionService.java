package com.registroescolar.backend.service.interfaces;

import com.registroescolar.backend.dto.InscripcionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InscripcionService {
    InscripcionDTO guardar(InscripcionDTO dto);
    InscripcionDTO actualizar(Long id, InscripcionDTO dto);
    InscripcionDTO obtenerPorId(Long id);
    List<InscripcionDTO> listar();
    void eliminar(Long id);

    Page<InscripcionDTO> listarPaginado(Pageable pageable);
}