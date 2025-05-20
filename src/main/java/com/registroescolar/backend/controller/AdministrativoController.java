package com.registroescolar.backend.controller;

import com.registroescolar.backend.dto.AdministrativoDTO;
import com.registroescolar.backend.dto.CursoDTO;
import com.registroescolar.backend.exception.RecursoNoEncontradoException;
import com.registroescolar.backend.service.interfaces.AdministrativoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/administrativos")
public class AdministrativoController {

    private final AdministrativoService administrativoService;

    public AdministrativoController(AdministrativoService administrativoService) {
        this.administrativoService = administrativoService;
    }

    @Operation(summary = "Crear administrativo")
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody @Valid AdministrativoDTO dto) {
        try {
            return ResponseEntity.status(201).body(administrativoService.guardar(dto));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al guardar administrativo.");
        }
    }

    @Operation(summary = "Actualizar administrativo por ID")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody @Valid AdministrativoDTO dto) {
        try {
            return ResponseEntity.ok(administrativoService.actualizar(id, dto));
        } catch (RecursoNoEncontradoException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al actualizar administrativo.");
        }
    }

    @Operation(summary = "Listar todos los administrativos")
    @GetMapping
    public ResponseEntity<List<AdministrativoDTO>> listar() {
        return ResponseEntity.ok(administrativoService.listar());
    }

    @Operation(summary = "Obtener administrativo por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(administrativoService.obtenerPorId(id));
        } catch (RecursoNoEncontradoException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al obtener administrativo.");
        }
    }

    @Operation(summary = "Eliminar administrativo por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            administrativoService.eliminar(id);
            return ResponseEntity.ok("Administrativo eliminado correctamente");
        } catch (RecursoNoEncontradoException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al eliminar administrativo.");
        }
    }


    // paginación
    @Operation(summary = "Listar administrativos paginados", description = "Parámetros: page, size, sort (ej. apellido,asc)")
    @GetMapping("/paginado")
    public ResponseEntity<?> listarPaginado(@ParameterObject Pageable pageable) {
        try {
            if (!pageable.getSort().isSorted()) {
                pageable = PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        Sort.by("apellido").ascending()
                );
            }
            return ResponseEntity.ok(administrativoService.listarPaginado(pageable));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al listar administrativos.");
        }
    }

    @Operation(summary = "Buscar administrativos por departamento")
    @GetMapping("/buscar-departamento")
    public ResponseEntity<?> buscarPorDepartamento(
            @RequestParam String departamento,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            return ResponseEntity.ok(administrativoService.buscarPorDepartamento(departamento, page, size));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al buscar por departamento.");
        }
    }

    @Operation(summary = "Buscar administrativos por nombre o departamento")
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPorNombreODepartamento(
            @RequestParam String filtro,
            @ParameterObject Pageable pageable) {
        try {
            return ResponseEntity.ok(administrativoService.buscarPorNombreODepartamento(filtro, pageable));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al buscar administrativos.");
        }
    }
}