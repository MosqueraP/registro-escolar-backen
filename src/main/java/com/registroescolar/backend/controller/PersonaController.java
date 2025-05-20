package com.registroescolar.backend.controller;

import com.registroescolar.backend.dto.PersonaDTO;
import com.registroescolar.backend.exception.OperacionInvalidaException;
import com.registroescolar.backend.exception.OperacionNoPermitidaException;
import com.registroescolar.backend.exception.RecursoNoEncontradoException;
import com.registroescolar.backend.repository.PersonRepository;
import com.registroescolar.backend.service.interfaces.PersonaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
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
@RequestMapping("/api/personas")
public class PersonaController {

    private final PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @PostMapping
    @Operation(summary = "Crear una nueva persona", description = "Crea una nueva persona y la almacena en la base de datos.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Persona creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o duplicados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> crear(@RequestBody @Valid PersonaDTO dto) {
        try {
            PersonaDTO nueva = personaService.crearPersona(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (OperacionInvalidaException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear persona.");
        }
    }

    @GetMapping
    @Operation(summary = "Listar todas las personas", description = "Retorna una lista con todas las personas registradas.")
    public ResponseEntity<List<PersonaDTO>> listar() {
        return ResponseEntity.ok(personaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener persona por ID", description = "Retorna una persona específica según su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Persona encontrada"),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno")
    })
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        try {
            PersonaDTO persona = personaService.obtenerPorId(id);
            return ResponseEntity.ok(persona);
        } catch (RecursoNoEncontradoException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener persona.");
        }
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar persona por ID", description = "Elimina una persona existente si no tiene relaciones asociadas.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Persona eliminada exitosamente"),
            @ApiResponse(responseCode = "403", description = "Operación no permitida"),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno")
    })
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            personaService.eliminarPersona(id);
            return ResponseEntity.noContent().build();
        } catch (RecursoNoEncontradoException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (OperacionNoPermitidaException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado al eliminar persona.");
        }
    }


    //paginación

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar persona por ID", description = "Modifica los datos de una persona existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Persona actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno")
    })
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody @Valid PersonaDTO dto) {
        try {
            PersonaDTO actualizada = personaService.actualizarPersona(id, dto);
            return ResponseEntity.ok(actualizada);
        } catch (RecursoNoEncontradoException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar persona.");
        }
    }

    @GetMapping("/paginado")
    @Operation(summary = "Listar personas paginadas", description = "Permite paginar las personas. Parámetros opcionales: page, size y sort.")
    public ResponseEntity<?> listarPaginado(Pageable pageable) {
        try {
            if (!pageable.getSort().isSorted()) {
                pageable = PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        Sort.by("nombre").ascending()
                );
            }
            return ResponseEntity.ok(personaService.listarPaginado(pageable));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al listar personas paginadas.");
        }
    }

    @GetMapping("/buscar-nombre")
    @Operation(summary = "Buscar personas por nombre", description = "Filtra personas por nombre con paginación.")
    public ResponseEntity<?> buscarPorNombre(
            @RequestParam String nombre,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nombre,asc") String[] sort
    ) {
        try {
            Sort sortOrder = Sort.by(Sort.Order.by(sort[0]));
            if (sort.length > 1 && sort[1].equalsIgnoreCase("desc")) {
                sortOrder = sortOrder.descending();
            }

            Pageable pageable = PageRequest.of(page, size, sortOrder);
            return ResponseEntity.ok(personaService.buscarPorNombre(nombre, pageable));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar personas.");
        }
    }

}