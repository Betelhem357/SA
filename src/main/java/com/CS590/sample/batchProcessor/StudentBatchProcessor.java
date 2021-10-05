package com.CS590.sample.batchProcessor;

import com.CS590.sample.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

// processing the Student data
public class StudentBatchProcessor implements ItemProcessor<Student,Student> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Student.class);
    @Override
    public Student process(Student student) throws Exception {
        Student transformedStudent = new Student();
        transformedStudent.setFirstName(student.getFirstName());
        transformedStudent.setLastName(student.getLastName());
        transformedStudent.setGpa(student.getGpa());
        transformedStudent.setId(student.getId());
        transformedStudent.setDateOfBirth(LocalDate.now().minusYears(student.getAge()).with(TemporalAdjusters.firstDayOfYear()));
        LOGGER.info("["+student+"] transformed in to ["+transformedStudent+"]");
        return transformedStudent;
    }
}
