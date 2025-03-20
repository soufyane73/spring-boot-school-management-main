package com.ikitama.management_of_schools.controller;

import com.ikitama.management_of_schools.domain.Grade;
import com.ikitama.management_of_schools.domain.Subject;
import com.ikitama.management_of_schools.service.SubjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subjects")
@AllArgsConstructor
public class SubjectController {
    SubjectService subjectService;

    @PostMapping(value = "create")
    public ResponseEntity<Subject> createSubject(@Validated @RequestBody Subject subject){
        return ResponseEntity.ok().body(subjectService.createSubject(subject));
    }

    @PutMapping(value = "update")
    public ResponseEntity<Subject> updateSubject(@Validated @RequestBody Subject subject){
        return ResponseEntity.ok().body(subjectService.updateSubject(subject));
    }

    @GetMapping(value = "/{ID}")
    public ResponseEntity<Subject> getSubject(@PathVariable("ID") Long ID){
        return ResponseEntity.ok().body(subjectService.getSubject(ID));
    }

    @GetMapping
    public ResponseEntity<List<Subject>> getSubjects(){
        return ResponseEntity.ok().body(subjectService.getSubjects());
    }

    @DeleteMapping(value = "{ID}/delete")
    public ResponseEntity<?> deleteSubject(@PathVariable("ID") Long ID){
        subjectService.deleteSubject(ID);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{subjectId}/add-to-classroom/{classroomId}")
    public ResponseEntity<Subject> addSubjectToClassroom(
            @PathVariable("subjectId") Long subjectId,
            @PathVariable("classroomId") Long classroomId) {
        return  ResponseEntity.ok().body(subjectService.addSubjectToClassroom(subjectId,classroomId));
    }

    @GetMapping("/{subjectId}/grades")
    public ResponseEntity<List<Grade>> getGradesInSubject(@PathVariable("subjectId") Long subjectId){

        return ResponseEntity.ok().body(subjectService.getGradesInSubject(subjectId));
    }
    
}
