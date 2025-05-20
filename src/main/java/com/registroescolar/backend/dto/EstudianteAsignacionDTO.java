package com.registroescolar.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteAsignacionDTO{
    private Long idPersona;
    private String numeroMatricula;
    private String grado;
}