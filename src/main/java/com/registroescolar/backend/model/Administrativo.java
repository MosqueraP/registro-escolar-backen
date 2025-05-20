package com.registroescolar.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "administrativo")
@PrimaryKeyJoinColumn(name = "id_persona")
public class Administrativo extends Persona {
    private String cargo;
    private String departamento;
}