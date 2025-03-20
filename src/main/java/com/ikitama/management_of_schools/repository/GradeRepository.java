package com.ikitama.management_of_schools.repository;

import com.ikitama.management_of_schools.entity.GradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<GradeEntity,Long> {
}
