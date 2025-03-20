package com.ikitama.management_of_schools.controller;

import com.ikitama.management_of_schools.domain.Classroom;
import com.ikitama.management_of_schools.domain.Student;
import com.ikitama.management_of_schools.domain.Subject;
import com.ikitama.management_of_schools.service.ClassroomService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classrooms")
@AllArgsConstructor
public class ClassroomController {

    ClassroomService classroomService;

    @PostMapping(value = "create")
    public ResponseEntity<Classroom> createClassroom(@Validated @RequestBody Classroom classroom){
        return ResponseEntity.ok().body(classroomService.createClassroom(classroom));
    }

    @PutMapping(value = "update")
    public ResponseEntity<Classroom> updateClassroom(@Validated @RequestBody Classroom classroom){
        return ResponseEntity.ok().body(classroomService.updateClassroom(classroom));
    }

    @GetMapping(value = "/{ID}")
    public ResponseEntity<Classroom> getClassroom(@PathVariable("ID") Long ID){
        return ResponseEntity.ok().body(classroomService.getClassroom(ID));
    }

    @GetMapping
    public ResponseEntity<List<Classroom>> getClassrooms(){
        return ResponseEntity.ok().body(classroomService.getClassrooms());
    }

    @DeleteMapping(value = "{ID}/delete")
    public ResponseEntity<?> deleteClassroom(@PathVariable("ID") Long ID){
        classroomService.deleteClassroom(ID);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{classroomId}/subjects")
    public ResponseEntity<List<Subject>> getSubjectsInClassroom(@PathVariable("classroomId") Long classroomId){
        //List<Subject> subjects = classroomService.getSubjectsInClassroom(classroomId);
        return ResponseEntity.ok().body(classroomService.getSubjectsInClassroom(classroomId));
    }

    @GetMapping("/{classroomId}/students")
    public ResponseEntity<List<Student>> getStudentsInClassroom(@PathVariable("classroomId") Long classroomId){
        //List<Student> students = classroomService.getStudentsInClassroom(classroomId);
        return ResponseEntity.ok().body(classroomService.getStudentsInClassroom(classroomId));
    }
}
