package com.registroescolar.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InscripcionDTO {
    private Long idInscripcion;
    private Long idEstudiante;
    private Long idCurso;
    private LocalDate fechaInscripcion;
}