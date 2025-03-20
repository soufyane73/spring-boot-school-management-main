package com.ikitama.management_of_schools.repository;

import com.ikitama.management_of_schools.entity.ClassroomEntity;
import com.ikitama.management_of_schools.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassroomRepository extends JpaRepository<ClassroomEntity,Long> {

    Optional<ClassroomEntity> findByNameIgnoreCase(String name);

}
