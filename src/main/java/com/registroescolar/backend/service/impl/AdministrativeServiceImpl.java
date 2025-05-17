package com.registroescolar.backend.service.impl;

import com.registroescolar.backend.dto.AdministrativeDTO;
import com.registroescolar.backend.exception.NotFoundException;
import com.registroescolar.backend.model.Administrative;
import com.registroescolar.backend.repository.AdministrativeRepository;
import com.registroescolar.backend.service.interfaces.AdministrativeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdministrativeServiceImpl implements AdministrativeService {

    @Autowired
    private AdministrativeRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AdministrativeDTO create(AdministrativeDTO dto) {
        Administrative administrative = modelMapper.map(dto, Administrative.class);
        Administrative saved = repository.save(administrative);
        return modelMapper.map(saved, AdministrativeDTO.class);
    }

    @Override
    public AdministrativeDTO update(Long id, AdministrativeDTO dto) {
        Administrative existing = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Administrative not found with id: " + id));

        modelMapper.map(dto, existing);
        Administrative updated = repository.save(existing);
        return modelMapper.map(updated, AdministrativeDTO.class);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Administrative not found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public AdministrativeDTO getById(Long id) {
        Administrative administrative = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Administrative not found with id: " + id));
        return modelMapper.map(administrative, AdministrativeDTO.class);
    }

    @Override
    public List<AdministrativeDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(administrative -> modelMapper.map(administrative, AdministrativeDTO.class))
                .collect(Collectors.toList());
    }
}