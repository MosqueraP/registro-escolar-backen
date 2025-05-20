package com.registroescolar.backend.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "profesor")
@PrimaryKeyJoinColumn(name = "id_persona")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Profesor extends Persona {
    private String especialidad;
    private LocalDate fechaContratacion;
}
