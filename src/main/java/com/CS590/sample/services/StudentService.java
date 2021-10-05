package com.CS590.sample.services;

import com.CS590.sample.model.Student;
import com.CS590.sample.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return this.studentRepository.findAll();
    }

    public Student createStudent(Student student) {
        return this.studentRepository.save(student);
    }

    public Optional<Student> getStudentById(Long id) {
        return this.studentRepository.findById(id);
    }
}
