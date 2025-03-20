package com.ikitama.management_of_schools.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Classroom {
    private Long id;
    private String name;
    private String level;
    private int year;
    private int maximum_capacity;
}
