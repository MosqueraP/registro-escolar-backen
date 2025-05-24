package com.registroescolar.backend.controller;

import com.registroescolar.backend.dto.CursoAsignacionDTO;
import com.registroescolar.backend.dto.CursoDTO;
import com.registroescolar.backend.exception.RecursoNoEncontradoException;
import com.registroescolar.backend.service.interfaces.CursoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @Operation(summary = "Crear un nuevo curso")
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody @Valid CursoDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.guardar(dto));
        } catch (RecursoNoEncontradoException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear curso.");
        }
    }

    @Operation(summary = "Listar todos los cursos")
    @GetMapping
    public ResponseEntity<List<CursoDTO>> listar() {
        return ResponseEntity.ok(cursoService.listar());
    }

    @Operation(summary = "Obtener curso por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(cursoService.obtenerPorId(id));
        } catch (RecursoNoEncontradoException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener curso.");
        }
    }

    @Operation(summary = "Actualizar curso por ID")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody @Valid CursoDTO dto) {
        try {
            return ResponseEntity.ok(cursoService.actualizar(id, dto));
        } catch (RecursoNoEncontradoException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar curso.");
        }
    }

    @Operation(summary = "Eliminar curso por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            cursoService.eliminar(id);
            return ResponseEntity.ok("Curso eliminado correctamente");
        } catch (RecursoNoEncontradoException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar curso.");
        }
    }

    // paginación
    @Operation(summary = "Listar cursos paginados", description = "Usar parámetros: page, size y sort (ej. nombre,asc)")
    @GetMapping("/paginado")
    public ResponseEntity<?> listarPaginado(@ParameterObject Pageable pageable) {
        try {
            if (!pageable.getSort().isSorted()) {
                pageable = PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        Sort.by("nombre").ascending()
                );
            }
            return ResponseEntity.ok(cursoService.listarPaginado(pageable));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al paginar cursos.");
        }
    }

    @Operation(summary = "Buscar cursos por nombre con paginación")
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPorNombre(
            @RequestParam String nombre,
            @ParameterObject Pageable pageable) {
        try {
            return ResponseEntity.ok(cursoService.buscarPorNombre(nombre, pageable));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar cursos.");
        }
    }

    @GetMapping("/asignacion")
    public ResponseEntity<List<CursoAsignacionDTO>> listarConProfesor() {
        return ResponseEntity.ok(cursoService.listarConProfesor());
    }


}