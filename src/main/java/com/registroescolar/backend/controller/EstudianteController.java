package com.registroescolar.backend.controller;

import com.registroescolar.backend.dto.EstudianteDTO;
import com.registroescolar.backend.exception.OperacionInvalidaException;
import com.registroescolar.backend.exception.RecursoNoEncontradoException;
import com.registroescolar.backend.service.interfaces.EstudianteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @PostMapping
    @Operation(summary = "Crear estudiante")
    public ResponseEntity<?> crear(@RequestBody @Valid EstudianteDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(estudianteService.guardar(dto));
        } catch (OperacionInvalidaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al crear estudiante.");
        }
    }

    @GetMapping
    @Operation(summary = "Listar estudiantes")
    public ResponseEntity<?> listar() {
        try {
            return ResponseEntity.ok(estudianteService.listar());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al listar estudiantes.");
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener estudiante por ID", description = "Retorna un estudiante específico mediante su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudiante encontrado"),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(estudianteService.obtenerPorId(id));
        } catch (RecursoNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al obtener estudiante.");
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar estudiante por ID")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            estudianteService.eliminar(id);
            return ResponseEntity.ok("Estudiante eliminado correctamente");
        } catch (RecursoNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar estudiante.");
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar estudiante")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody @Valid EstudianteDTO dto) {
        try {
            return ResponseEntity.ok(estudianteService.actualizar(id, dto));
        } catch (RecursoNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al actualizar estudiante.");
        }
    }

    @GetMapping("/paginado")
    @Operation(
            summary = "Listar estudiantes paginados",
            description = "Parámetros: page, size, sort. Ej: ?page=0&size=5&sort=nombre,asc"
    )
    public ResponseEntity<?> listarPaginado(@ParameterObject Pageable pageable) {
        try {
            if (!pageable.getSort().isSorted()) {
                pageable = PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        Sort.by("nombre").ascending()
                );
            }
            return ResponseEntity.ok(estudianteService.listarPaginado(pageable));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al obtener estudiantes paginados.");
        }
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar estudiantes por nombre con paginación")
    public ResponseEntity<?> buscarPorNombre(@RequestParam String nombre, @ParameterObject Pageable pageable) {
        try {
            return ResponseEntity.ok(estudianteService.buscarPorNombre(nombre, pageable));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al buscar estudiantes.");
        }
    }
}