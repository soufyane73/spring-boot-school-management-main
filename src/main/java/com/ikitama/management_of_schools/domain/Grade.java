package com.ikitama.management_of_schools.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Grade {
    private Long id;
    private int score;
    private String comment;
    private String type;
    private Student student;
    private Subject subject;
}
