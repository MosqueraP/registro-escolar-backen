package com.registroescolar.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EstudianteRegistroDTO extends PersonaDTO {
    @NotBlank
    private String numeroMatricula;
    @NotBlank
    private String grado;
}
