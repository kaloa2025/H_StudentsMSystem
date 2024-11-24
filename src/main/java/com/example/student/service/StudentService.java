package com.example.student.service;

import com.example.student.model.Student;
import com.example.student.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepo studentRepository;

    public Optional<Student> registerStudent(Student student) {
        if (studentRepository.findByEmail(student.getEmail()).isPresent()) {
            return Optional.empty(); // Prevent multiple registrations with same email
        }
        return Optional.of(studentRepository.save(student));
    }

    public Optional<Student> updateStudent(Long id, Student student) {
        return studentRepository.findById(id).map(existingStudent -> {
            existingStudent.setName(student.getName());
            existingStudent.setPhone(student.getPhone());
            return studentRepository.save(existingStudent);
        });
    }

    public Optional<Student> getStudent(Long id) {
        return studentRepository.findById(id);
    }
}