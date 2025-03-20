package com.ikitama.management_of_schools.repository;

import com.ikitama.management_of_schools.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentEntity,Long> {
    boolean existsByRegistrationNumber(String registrationNumber);

}
