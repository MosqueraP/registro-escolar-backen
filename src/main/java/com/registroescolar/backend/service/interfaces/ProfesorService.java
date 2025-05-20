package com.registroescolar.backend.service.interfaces;

import com.registroescolar.backend.dto.ProfesorAsignacionDTO;
import com.registroescolar.backend.dto.ProfesorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProfesorService {
    ProfesorDTO guardar(ProfesorDTO dto);
    ProfesorDTO actualizar(Long id, ProfesorDTO dto);
    List<ProfesorDTO> listar();
    ProfesorDTO obtenerPorId(Long id);
    void eliminar(Long id);

    Page<ProfesorDTO> listarPaginado(Pageable pageable);

    Page<ProfesorDTO> buscarPorNombre(String nombre, int page, int size);



}