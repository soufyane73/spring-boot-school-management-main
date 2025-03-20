package com.ikitama.management_of_schools.mapper;

import com.ikitama.management_of_schools.domain.Subject;
import com.ikitama.management_of_schools.entity.SubjectEntity;
import org.mapstruct.Mapper;

@Mapper
public interface SubjectMapper {
    Subject toSubject(SubjectEntity subjectEntity);
    SubjectEntity fromSubject(Subject subject);
}
