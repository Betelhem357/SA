package com.CS590.sample.controller;

import com.CS590.sample.model.Student;
import com.CS590.sample.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // get all students method
    @GetMapping("/students")
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    public ResponseEntity<List<Student>> getAllStudents(){
        return new ResponseEntity<List<Student>>(this.studentService.getStudents(),HttpStatus.OK);
    }

    // create Student method
    @PostMapping("/students")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Student> createStudent(@RequestBody Student student){
        return new ResponseEntity<Student>(this.studentService.createStudent(student),HttpStatus.OK);
    }

    // get Student by Id
    @GetMapping("/student/{Id}")
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    public ResponseEntity<Student> getStudentById(@PathVariable Long Id){
        return new ResponseEntity<Student>(this.studentService.getStudentById(Id).get(),HttpStatus.OK);
    }

}
