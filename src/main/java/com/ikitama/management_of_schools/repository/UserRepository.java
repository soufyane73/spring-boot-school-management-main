package com.ikitama.management_of_schools.repository;

import com.ikitama.management_of_schools.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByName(String name);
}
