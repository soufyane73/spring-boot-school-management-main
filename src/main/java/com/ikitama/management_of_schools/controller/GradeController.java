package com.ikitama.management_of_schools.controller;

import com.ikitama.management_of_schools.domain.Grade;
import com.ikitama.management_of_schools.service.GradeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/grades")
@RestController
@AllArgsConstructor
public class GradeController {

    GradeService gradeService;

    @PostMapping(value = "create")
    public ResponseEntity<Grade> createGrade(@Validated @RequestBody Grade grade){
        return ResponseEntity.ok().body(gradeService.createGrade(grade));
    }

    @PutMapping(value = "update")
    public ResponseEntity<Grade> updateGrade(@Validated @RequestBody Grade grade){
        return ResponseEntity.ok().body(gradeService.updateGrade(grade));
    }

    @GetMapping(value = "/{ID}")
    public ResponseEntity<Grade> getGrade(@PathVariable("ID") Long ID){
        return ResponseEntity.ok().body(gradeService.getGrade(ID));
    }

    @GetMapping
    public ResponseEntity<List<Grade>> getGrades(){
        return ResponseEntity.ok().body(gradeService.getGrades());
    }

    @DeleteMapping(value = "{ID}/delete")
    public ResponseEntity<?> deleteGrade(@PathVariable("ID") Long ID){
        gradeService.deleteGrade(ID);
        return ResponseEntity.ok().build();
    }

}
