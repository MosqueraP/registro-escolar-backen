package com.registroescolar.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InscripcionDetalleDTO {
    private Long idInscripcion;
    private String nombreEstudiante;
    private String nombreCurso;
    private LocalDate fechaInscripcion;
}