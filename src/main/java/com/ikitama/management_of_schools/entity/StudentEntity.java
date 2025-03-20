package com.ikitama.management_of_schools.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Table(name = "students")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(nullable = false,unique = true)
    private String registrationNumber;

    @Column(nullable = false)
    private String full_name;
    private Date date_of_birth;
    private String address;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String phone_number;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne
    private ClassroomEntity classroom;

    @OneToMany(mappedBy = "student")
    private List<GradeEntity> grades;
}
