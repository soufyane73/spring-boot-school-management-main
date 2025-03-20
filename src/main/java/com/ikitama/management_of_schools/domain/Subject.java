package com.ikitama.management_of_schools.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Subject {
    private Long id;
    private String name;
    private  int coefficient;
}
