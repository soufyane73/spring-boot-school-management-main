package com.ikitama.management_of_schools.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Student {
    private Long id;
    private String registrationNumber;
    private String full_name;
    private Date date_of_birth;
    private String address;
    private String email;
    private String phone_number;
    private String gender;
    private Classroom classroom;
}
