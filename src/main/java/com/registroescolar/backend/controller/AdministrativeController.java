package com.registroescolar.backend.controller;

import com.registroescolar.backend.dto.AdministrativeDTO;
import com.registroescolar.backend.service.interfaces.AdministrativeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/administratives")
public class AdministrativeController {

    @Autowired
    private AdministrativeService administrativeService;

    @PostMapping
    public AdministrativeDTO create(@Valid @RequestBody AdministrativeDTO dto) {
        return administrativeService.create(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdministrativeDTO> update(@PathVariable Long id, @Valid @RequestBody AdministrativeDTO dto) {
        return ResponseEntity.ok(administrativeService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        administrativeService.delete(id);
        return ResponseEntity.ok("Administrative deleted successfully.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdministrativeDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(administrativeService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<AdministrativeDTO>> getAll() {
        return ResponseEntity.ok(administrativeService.getAll());
    }
}