package com.registroescolar.backend.service.interfaces;

import com.registroescolar.backend.dto.CursoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CursoService {
    CursoDTO guardar(CursoDTO dto);
    CursoDTO actualizar(Long id, CursoDTO dto);
    List<CursoDTO> listar();
    CursoDTO obtenerPorId(Long id);
    void eliminar(Long id);

    Page<CursoDTO> listarPaginado(Pageable pageable);

    Page<CursoDTO> buscarPorNombre(String nombre, Pageable pageable);


}