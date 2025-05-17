package com.registroescolar.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorDTO {
    private Long id;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @Pattern(regexp = "\\d{7,15}", message = "Phone number must be valid")
    private String phone;

    @Past(message = "Birthdate must be in the past")
    private LocalDate birthDate;

    @NotBlank(message = "Specialization is required")
    private String specialization;

    @Past(message = "Hire date must be in the past")
    private LocalDate hireDate;
}