package com.registroescolar.backend.service.impl;

import com.registroescolar.backend.dto.StudentDTO;
import com.registroescolar.backend.exception.DuplicateResourceException;
import com.registroescolar.backend.exception.NotFoundException;
import com.registroescolar.backend.model.Student;
import com.registroescolar.backend.repository.StudentRepository;
import com.registroescolar.backend.service.interfaces.StudentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    @Override
    public StudentDTO create(StudentDTO dto) {
        if (studentRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Email already registered: " + dto.getEmail());
        }

        if (studentRepository.findByEnrollmentNumber(dto.getEnrollmentNumber()).isPresent()) {
            throw new DuplicateResourceException("Enrollment number already registered: " + dto.getEnrollmentNumber());
        }

        Student student = modelMapper.map(dto, Student.class);
        student = studentRepository.save(student);
        return modelMapper.map(student, StudentDTO.class);
    }


    @Override
    public List<StudentDTO> getAll() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(student -> modelMapper.map(student, StudentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public StudentDTO getById(Long id) {
        Student student = studentRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Student not found"));
                .orElseThrow(() -> new NotFoundException("Student not found with ID: " + id));

        return modelMapper.map(student, StudentDTO.class);
    }

    @Override
    public StudentDTO update(Long id, StudentDTO dto) {
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Solo actualizamos los campos, sin tocar el ID
        modelMapper.map(dto, existing);
        studentRepository.save(existing);
        return modelMapper.map(existing, StudentDTO.class);
    }


    @Override
    public void delete(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found with id: " + id));
        studentRepository.delete(student);
    }
}
