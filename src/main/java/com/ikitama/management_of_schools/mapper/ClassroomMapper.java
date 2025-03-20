package com.ikitama.management_of_schools.mapper;
import com.ikitama.management_of_schools.domain.Classroom;
import com.ikitama.management_of_schools.entity.ClassroomEntity;
import org.mapstruct.Mapper;

@Mapper
public interface ClassroomMapper {

    Classroom toClassroom(ClassroomEntity classroomEntity);
    ClassroomEntity fromClassroom(Classroom classroom);
}
