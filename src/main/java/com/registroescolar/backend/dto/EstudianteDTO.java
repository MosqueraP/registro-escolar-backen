package com.registroescolar.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para actualizar estudiante")
public class EstudianteDTO extends PersonaDTO {

//    @Schema(description = "Número de matrícula (no se puede modificar)", accessMode = Schema.AccessMode.READ_ONLY)
    @NotBlank
    private String numeroMatricula;

    private String grado;
}
