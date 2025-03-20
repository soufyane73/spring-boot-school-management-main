package com.ikitama.management_of_schools.mapper;

import com.ikitama.management_of_schools.domain.Grade;
import com.ikitama.management_of_schools.entity.GradeEntity;
import org.mapstruct.Mapper;

@Mapper
public interface GradeMapper {

    Grade toGrade(GradeEntity noteEntity);

    GradeEntity fromGrade(Grade note);
}
