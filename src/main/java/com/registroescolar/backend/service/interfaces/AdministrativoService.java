package com.registroescolar.backend.service.interfaces;

import com.registroescolar.backend.dto.AdministrativoDTO;
import com.registroescolar.backend.dto.ProfesorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdministrativoService {
    AdministrativoDTO guardar(AdministrativoDTO dto);
    AdministrativoDTO actualizar(Long id, AdministrativoDTO dto);
    List<AdministrativoDTO> listar();
    AdministrativoDTO obtenerPorId(Long id);
    void eliminar(Long id);

    Page<AdministrativoDTO> listarPaginado(Pageable pageable);
    Page<AdministrativoDTO> buscarPorDepartamento(String departamento, int page, int size);
    Page<AdministrativoDTO> buscarPorNombreODepartamento(String filtro, Pageable pageable);


}