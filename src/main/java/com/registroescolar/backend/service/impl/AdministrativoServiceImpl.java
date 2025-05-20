package com.registroescolar.backend.service.impl;

import com.registroescolar.backend.dto.AdministrativoDTO;
import com.registroescolar.backend.dto.ProfesorDTO;
import com.registroescolar.backend.exception.RecursoNoEncontradoException;
import com.registroescolar.backend.model.Administrativo;
import com.registroescolar.backend.repository.AdministrativoRepository;
import com.registroescolar.backend.service.interfaces.AdministrativoService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdministrativoServiceImpl implements AdministrativoService {

    private final AdministrativoRepository administrativoRepository;
    private final ModelMapper modelMapper;

    public AdministrativoServiceImpl(AdministrativoRepository administrativoRepository, ModelMapper modelMapper) {
        this.administrativoRepository = administrativoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AdministrativoDTO guardar(AdministrativoDTO dto) {
        Administrativo administrativo = modelMapper.map(dto, Administrativo.class);
        administrativo = administrativoRepository.save(administrativo);
        return modelMapper.map(administrativo, AdministrativoDTO.class);
    }

    @Override
    public AdministrativoDTO actualizar(Long id, AdministrativoDTO dto) {
        Administrativo administrativo = administrativoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Administrativo con ID " + id + " no encontrado"));

        dto.setIdPersona(administrativo.getIdPersona()); // No permitir cambiar ID
        modelMapper.map(dto, administrativo);
        administrativo = administrativoRepository.save(administrativo);
        return modelMapper.map(administrativo, AdministrativoDTO.class);
    }


    @Override
    public List<AdministrativoDTO> listar() {
        return administrativoRepository.findAll().stream()
                .map(a -> modelMapper.map(a, AdministrativoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public AdministrativoDTO obtenerPorId(Long id) {
        Administrativo administrativo = administrativoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Administrativo con ID " + id + " no encontrado"));
        return modelMapper.map(administrativo, AdministrativoDTO.class);
    }

    @Override
    public void eliminar(Long id) {
        if (!administrativoRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Administrativo con ID " + id + " no encontrado para eliminar.");
        }
        administrativoRepository.deleteById(id);
    }

    @Override
    public Page<AdministrativoDTO> listarPaginado(Pageable pageable) {
        return administrativoRepository.findAll(pageable)
                .map(administrativo -> modelMapper.map(administrativo, AdministrativoDTO.class));
    }

    @Override
    public Page<AdministrativoDTO> buscarPorDepartamento(String departamento, int page, int size) {
        return administrativoRepository
                .findByDepartamentoContainingIgnoreCase(departamento, PageRequest.of(page, size))
                .map(a -> modelMapper.map(a, AdministrativoDTO.class));
    }
    @Override
    public Page<AdministrativoDTO> buscarPorNombreODepartamento(String filtro, Pageable pageable) {
        return administrativoRepository
                .findByNombreContainingIgnoreCaseOrDepartamentoContainingIgnoreCase(filtro, filtro, pageable)
                .map(admin -> modelMapper.map(admin, AdministrativoDTO.class));
    }

}
