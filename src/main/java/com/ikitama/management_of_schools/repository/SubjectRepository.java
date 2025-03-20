package com.ikitama.management_of_schools.repository;

import com.ikitama.management_of_schools.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<SubjectEntity,Long> {
    Optional<SubjectEntity> findByNameIgnoreCase(String name);

}
