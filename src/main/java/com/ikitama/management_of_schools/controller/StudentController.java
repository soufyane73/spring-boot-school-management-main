package com.ikitama.management_of_schools.controller;

import com.ikitama.management_of_schools.domain.Classroom;
import com.ikitama.management_of_schools.domain.Grade;
import com.ikitama.management_of_schools.domain.Student;
import com.ikitama.management_of_schools.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/students")
@AllArgsConstructor
public class StudentController {

    StudentService studentService;

    @PostMapping(value = "create")
    public ResponseEntity<Student> createStudent(@Validated @RequestBody Student student){
        return ResponseEntity.ok().body(studentService.createStudent(student));
    }

    @PutMapping(value = "update")
    public ResponseEntity<Student> updateStudent(@Validated @RequestBody Student student){
        return ResponseEntity.ok().body(studentService.updateStudent(student));
    }

    @GetMapping(value = "/{ID}")
    public ResponseEntity<Student> getStudent(@PathVariable("ID") Long ID){
        return ResponseEntity.ok().body(studentService.getStudent(ID));
    }

    @GetMapping
    public ResponseEntity<List<Student>> getStudents(){
        return ResponseEntity.ok().body(studentService.getStudents());
    }

    @DeleteMapping(value = "{ID}/delete")
    public ResponseEntity<?> deleteStudent(@PathVariable("ID") Long ID){
        studentService.deleteStudent(ID);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{studentId}/grades")
    public ResponseEntity<List<Grade>> getGradesOfStudent(@PathVariable("studentId") Long studentId){

        return ResponseEntity.ok().body(studentService.getGradesOfStudent(studentId));
    }

    @GetMapping("/classroom")
    public ResponseEntity<Classroom> getStudentClassroom(@RequestBody Student student) {
        if (student.getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(studentService.getStudentClassroom(student.getId()));
    }

    @GetMapping("/check-registration-number/{registrationNumber}")
    public ResponseEntity<Boolean> checkRegistrationNumberExists(@PathVariable String registrationNumber) {
        boolean exists = studentService.checkRegistrationNumberExists(registrationNumber);
        return ResponseEntity.ok(exists);
    }
}
