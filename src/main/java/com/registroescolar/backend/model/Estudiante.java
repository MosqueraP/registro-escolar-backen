package com.registroescolar.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "estudiante")
@PrimaryKeyJoinColumn(name = "id")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Estudiante extends Persona {
    @Column(unique = true)
    private String numeroMatricula;
    private String grado;

//    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Enrollment> enrollments;

}
