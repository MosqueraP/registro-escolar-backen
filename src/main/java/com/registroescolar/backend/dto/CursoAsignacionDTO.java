package com.registroescolar.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursoAsignacionDTO {
    private Long idCurso;
    private String nombre;
    private String descripcion;
    private Integer creditos;

    private Long idProfesor;
    private String nombreProfesor;
    private String apellidoProfesor;
}

