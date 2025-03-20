package com.ikitama.management_of_schools.entity;

import com.ikitama.management_of_schools.domain.Classroom;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "subjects")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private  int coefficient;

    @ManyToMany(mappedBy = "subjects")
    private List<ClassroomEntity> classrooms;

    @OneToMany(mappedBy = "subject")
    private List<GradeEntity> grades;
}
