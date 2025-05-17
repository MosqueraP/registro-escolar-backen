package com.registroescolar.backend.service.interfaces;

import com.registroescolar.backend.dto.AdministrativeDTO;

import java.util.List;

public interface AdministrativeService {
    AdministrativeDTO create(AdministrativeDTO dto);
    AdministrativeDTO update(Long id, AdministrativeDTO dto);
    void delete(Long id);
    AdministrativeDTO getById(Long id);
    List<AdministrativeDTO> getAll();
}