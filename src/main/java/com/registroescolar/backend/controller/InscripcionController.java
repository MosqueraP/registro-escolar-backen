package com.registroescolar.backend.controller;

import com.registroescolar.backend.dto.InscripcionDTO;
import com.registroescolar.backend.dto.InscripcionDetalleDTO;
import com.registroescolar.backend.service.interfaces.InscripcionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    private final InscripcionService inscripcionService;

    public InscripcionController(InscripcionService inscripcionService) {
        this.inscripcionService = inscripcionService;
    }

    @PostMapping
    @Operation(summary = "Crear inscripción", description = "Registra una nueva inscripción a un curso.")
    public ResponseEntity<?> guardar(@RequestBody @Valid InscripcionDTO dto) {
        try {
            return ResponseEntity.status(201).body(inscripcionService.guardar(dto));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error al registrar la inscripción.");
        }
    }

    @GetMapping
    @Operation(summary = "Listar inscripciones", description = "Devuelve una lista de todas las inscripciones.")
    public ResponseEntity<List<InscripcionDTO>> listar() {
        return ResponseEntity.ok(inscripcionService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener inscripción por ID", description = "Devuelve los detalles de una inscripción específica.")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(inscripcionService.obtenerPorId(id));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar inscripción", description = "Actualiza una inscripción existente.")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody @Valid InscripcionDTO dto) {
        try {
            return ResponseEntity.ok(inscripcionService.actualizar(id, dto));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error al actualizar inscripción.");
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar inscripción", description = "Elimina una inscripción por su ID.")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            inscripcionService.eliminar(id);
            return ResponseEntity.ok("Inscripción eliminada correctamente.");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error al eliminar inscripción.");
        }
    }

    @GetMapping("/paginado")
    @Operation(summary = "Listar inscripciones paginadas",
            description = "Permite paginar las inscripciones. Parámetros opcionales: page, size, sort.")
    public ResponseEntity<?> listarPaginado(@Parameter(hidden = true) Pageable pageable) {
        try {
            if (!pageable.getSort().isSorted()) {
                pageable = PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        Sort.by("fechaInscripcion").descending()
                );
            }
            return ResponseEntity.ok(inscripcionService.listarPaginado(pageable));
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error al listar inscripciones paginadas.");
        }
    }


    @GetMapping("/detalles")
    @Operation(summary = "Ver inscripción detallada", description = "Ver nombre Curso + Nombre Curso.")
    public ResponseEntity<List<InscripcionDetalleDTO>> listarConNombres() {
        return ResponseEntity.ok(inscripcionService.listarConNombres());
    }

    @GetMapping("/con-nombres/paginado")
    public ResponseEntity<Page<InscripcionDetalleDTO>> listarConNombresPaginado(Pageable pageable) {
        return ResponseEntity.ok(inscripcionService.listarConNombresPaginado(pageable));
    }
}