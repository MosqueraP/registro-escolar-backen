package com.registroescolar.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonaDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long idPersona;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    @Email
    @NotBlank(message = "Email is required")
    private String email;
    private String telefono;
}