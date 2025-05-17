package com.registroescolar.backend.service.interfaces;

import com.registroescolar.backend.dto.ProfessorDTO;

import java.util.List;

public interface ProfessorService {
    List<ProfessorDTO> findAll();
    ProfessorDTO findById(Long id);
    ProfessorDTO create(ProfessorDTO dto);
    ProfessorDTO update(Long id, ProfessorDTO dto);
    void delete(Long id);
}
