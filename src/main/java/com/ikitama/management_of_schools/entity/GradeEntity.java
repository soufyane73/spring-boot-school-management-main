package com.ikitama.management_of_schools.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "grades")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GradeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int score;

    @Column
    private String comment;

    @Enumerated(EnumType.STRING)
    private GradeType type;

    @ManyToOne
    private StudentEntity student;

    @ManyToOne
    private SubjectEntity subject;
}
