package com.registroescolar.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfesorAsignacionDTO extends PersonaDTO{
    private Long idPersona;  // ID de la persona existente
    private String especialidad;
    private LocalDate fechaContratacion;
}
