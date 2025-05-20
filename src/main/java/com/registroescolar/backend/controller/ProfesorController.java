package com.registroescolar.backend.controller;

import com.registroescolar.backend.dto.ProfesorAsignacionDTO;
import com.registroescolar.backend.dto.ProfesorDTO;
import com.registroescolar.backend.exception.BadRequestException;
import com.registroescolar.backend.exception.NotFoundException;
import com.registroescolar.backend.exception.RecursoNoEncontradoException;
import com.registroescolar.backend.service.interfaces.ProfesorService;
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
@RequestMapping("/api/profesores")
public class ProfesorController {

    private final ProfesorService profesorService;

    public ProfesorController(ProfesorService profesorService) {
        this.profesorService = profesorService;
    }

    @PostMapping
    @Operation(summary = "Crear profesor", description = "Crea un nuevo profesor en el sistema.")
    public ResponseEntity<?> guardar(@RequestBody @Valid ProfesorDTO dto) {
        try {
            return ResponseEntity.status(201).body(profesorService.guardar(dto));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al crear profesor.");
        }
    }

    @GetMapping
    @Operation(summary = "Listar profesores", description = "Obtiene todos los profesores.")
    public ResponseEntity<List<ProfesorDTO>> listar() {
        return ResponseEntity.ok(profesorService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener profesor por ID", description = "Retorna los datos de un profesor por su ID.")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(profesorService.obtenerPorId(id));
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al obtener profesor.");
        }
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
//        profesorService.eliminar(id);
//        return ResponseEntity.noContent().build();
//    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar profesor", description = "Elimina un profesor por su ID.")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            profesorService.eliminar(id);
            return ResponseEntity.ok("Profesor eliminado correctamente.");
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (BadRequestException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al eliminar profesor.");
        }
    }



    @PutMapping("/{id}")
    @Operation(summary = "Actualizar profesor", description = "Actualiza la información de un profesor.")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody @Valid ProfesorDTO dto) {
        try {
            return ResponseEntity.ok(profesorService.actualizar(id, dto));
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al actualizar profesor.");
        }
    }


    @GetMapping("/paginado")
    @Operation(summary = "Listar profesores paginados", description = "Permite obtener profesores con paginación.")
    public ResponseEntity<Page<ProfesorDTO>> listarPaginado(@Parameter(hidden = true) Pageable pageable) {
        if (!pageable.getSort().isSorted()) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("nombre").ascending());
        }
        return ResponseEntity.ok(profesorService.listarPaginado(pageable));
    }

    @GetMapping("/buscar-nombre")
    @Operation(summary = "Buscar profesores por nombre", description = "Permite buscar profesores usando parte de su nombre.")
    public Page<ProfesorDTO> buscarPorNombre(
            @Parameter(description = "Nombre o parte del nombre") @RequestParam String nombre,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return profesorService.buscarPorNombre(nombre, page, size);
    }
}