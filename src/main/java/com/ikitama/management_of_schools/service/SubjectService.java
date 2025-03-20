package com.ikitama.management_of_schools.service;

import com.ikitama.management_of_schools.domain.Grade;
import com.ikitama.management_of_schools.domain.Subject;
import com.ikitama.management_of_schools.entity.ClassroomEntity;

import com.ikitama.management_of_schools.entity.SubjectEntity;
import com.ikitama.management_of_schools.exception.EntityNotFoundException;
import com.ikitama.management_of_schools.exception.RequestException;
import com.ikitama.management_of_schools.mapper.ClassroomMapper;
import com.ikitama.management_of_schools.mapper.GradeMapper;
import com.ikitama.management_of_schools.mapper.SubjectMapper;
import com.ikitama.management_of_schools.repository.ClassroomRepository;
import com.ikitama.management_of_schools.repository.SubjectRepository;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class SubjectService {

    SubjectRepository subjectRepository;
    ClassroomRepository classroomRepository;

    SubjectMapper subjectMapper;
    ClassroomMapper classroomMapper;
    GradeMapper gradeMapper;

    MessageSource messageSource;

    private static final Logger logger = LogManager.getLogger(SubjectService.class);

    public Subject createSubject(Subject subject){

        subjectRepository.findByNameIgnoreCase(subject.getName())
                .ifPresent(subjectEntity -> {
                    throw new RequestException(messageSource.getMessage("subject.exists",new Object[]{subject.getName()},
                            Locale.getDefault()), HttpStatus.CONFLICT);
                });

        return subjectMapper.toSubject(subjectRepository.save(subjectMapper.fromSubject(subject)));
    }

    public Subject updateSubject(Subject subject){

        return subjectRepository.findById(subject.getId()).map(subjectEntity -> subjectMapper.toSubject(
                subjectRepository.save(subjectMapper.fromSubject(subject))
        )).orElseThrow(
                ()->
                        new EntityNotFoundException(messageSource.getMessage("subject.notFound",new Object[]{subject.getId()},
                                Locale.getDefault()))
        );
    }

    public Subject getSubject(Long ID){
        return subjectMapper.toSubject(subjectRepository.findById(ID).orElseThrow(
                ()-> new EntityNotFoundException(messageSource.getMessage("subject.notFound",new Object[]{ID},
                        Locale.getDefault()))
        ));
    }

    public List<Subject> getSubjects(){
        return subjectRepository.findAll().stream()
                .map(subjectMapper::toSubject)
                .collect(Collectors.toList());
    }

    public void deleteSubject(Long ID) {
        if (subjectRepository.findById(ID).isPresent()) {
            try {
                subjectRepository.deleteById(ID);
                logger.info("Le subject avec l'ID {} a été supprimé.", ID);

            } catch (Exception exception) {
                logger.error("Erreur lors de la suppression du subject avec l'ID {} : {}", ID, exception.getMessage());

                throw new RequestException(
                        messageSource.getMessage("subject.errorDeletion", new Object[]{ID},
                                Locale.getDefault()),
                        HttpStatus.CONFLICT
                );
            }
        } else {
            logger.warn("Tentative de suppression d'un subject avec l'ID {} qui n'existe pas.", ID);

            throw new EntityNotFoundException(messageSource.getMessage("subject.notFound", new Object[]{ID},
                    Locale.getDefault()));
        }
    }

    public Subject addSubjectToClassroom(Long subjectId, Long classroomId){
        Subject subject = getSubject(subjectId);
        ClassroomEntity classroomEntity = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("classroom.notFound",
                        new Object[]{classroomId}, Locale.getDefault())));

        SubjectEntity subjectEntity=subjectMapper.fromSubject(subject);
        classroomEntity.getSubjects().add(subjectEntity);

        classroomRepository.save(classroomEntity);
        return subject;
    }

    public List<Grade> getGradesInSubject(Long subjectId){

        SubjectEntity subjectEntity=subjectRepository.findById(subjectId)
                .orElseThrow(()->new EntityNotFoundException(messageSource.getMessage("subject.notFound",
                        new Object[]{subjectId},Locale.getDefault())));

        return subjectEntity.getGrades().stream()
                .map(gradeEntity -> {
                    logger.debug("Mapping gradeEntity to grade {}",gradeEntity.getId());

                    return gradeMapper.toGrade(gradeEntity);
                })
                .collect(Collectors.toList());

    }

}
