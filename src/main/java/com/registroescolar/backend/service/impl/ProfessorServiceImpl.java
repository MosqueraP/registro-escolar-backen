package com.registroescolar.backend.service.impl;

import com.registroescolar.backend.dto.ProfessorDTO;
import com.registroescolar.backend.exception.NotFoundException;
import com.registroescolar.backend.model.Professor;
import com.registroescolar.backend.repository.ProfessorRepository;
import com.registroescolar.backend.service.interfaces.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class ProfessorServiceImpl implements ProfessorService {

    private final ProfessorRepository professorRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ProfessorDTO> findAll() {
        return professorRepository.findAll()
                .stream()
                .map(p -> modelMapper.map(p, ProfessorDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProfessorDTO findById(Long id) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Professor not found with id: " + id));
        return modelMapper.map(professor, ProfessorDTO.class);
    }

    @Override
    public ProfessorDTO create(ProfessorDTO dto) {
        Professor professor = modelMapper.map(dto, Professor.class);
        professorRepository.save(professor);
        return modelMapper.map(professor, ProfessorDTO.class);
    }

    @Override
    public ProfessorDTO update(Long id, ProfessorDTO dto) {
        Professor existing = professorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Professor not found with id: " + id));
        modelMapper.map(dto, existing);
        professorRepository.save(existing);
        return modelMapper.map(existing, ProfessorDTO.class);
    }

    @Override
    public void delete(Long id) {
        Professor existing = professorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Professor not found with id: " + id));
        professorRepository.delete(existing);
    }
}
