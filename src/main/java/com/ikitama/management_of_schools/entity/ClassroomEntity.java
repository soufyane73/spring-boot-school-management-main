package com.ikitama.management_of_schools.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "classrooms")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClassroomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String level;
    private int year;
    private int maximum_capacity;

    @OneToMany(mappedBy = "classroom")
    private List<StudentEntity> students;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(
            name = "classroom_subject",
            joinColumns = @JoinColumn(name = "classroom_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private List<SubjectEntity> subjects;
}
