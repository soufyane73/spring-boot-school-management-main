package com.ikitama.management_of_schools.domain;

import com.ikitama.management_of_schools.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private Long id;
    private String name;
    private String password;
    private String email;
    private String role;
}
