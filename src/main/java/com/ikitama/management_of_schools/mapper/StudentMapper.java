package com.ikitama.management_of_schools.mapper;

import com.ikitama.management_of_schools.domain.Student;
import com.ikitama.management_of_schools.entity.StudentEntity;
import org.mapstruct.Mapper;

@Mapper
public interface StudentMapper {

    Student toStudent(StudentEntity studentEntity);
    StudentEntity fromStudent(Student student);
}
