package com.registroescolar.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursoDTO {
    private Long idCurso;
    private String nombre;
    private String descripcion;
    private Integer creditos;
    private Long idProfesor;
}