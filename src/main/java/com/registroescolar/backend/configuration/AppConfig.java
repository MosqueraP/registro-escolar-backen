package com.registroescolar.backend.configuration;

import com.registroescolar.backend.dto.ProfessorDTO;
import com.registroescolar.backend.dto.StudentDTO;
import com.registroescolar.backend.model.Professor;
import com.registroescolar.backend.model.Student;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Ignora el ID al mapear de DTO a entidad

        // Ignora el ID al mapear de StudentDTO a Student
        modelMapper.typeMap(StudentDTO.class, Student.class)
                .addMappings(mapper -> mapper.skip(Student::setId));

        // Ignora el ID al mapear de ProfessorDTO a Professor
        modelMapper.typeMap(ProfessorDTO.class, Professor.class)
                .addMappings(mapper -> mapper.skip(Professor::setId));

        return modelMapper;
    }
}