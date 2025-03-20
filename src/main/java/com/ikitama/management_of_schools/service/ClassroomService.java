package com.ikitama.management_of_schools.service;

import com.ikitama.management_of_schools.domain.Classroom;
import com.ikitama.management_of_schools.domain.Student;
import com.ikitama.management_of_schools.domain.Subject;
import com.ikitama.management_of_schools.entity.ClassroomEntity;
import com.ikitama.management_of_schools.exception.EntityNotFoundException;
import com.ikitama.management_of_schools.exception.RequestException;
import com.ikitama.management_of_schools.mapper.ClassroomMapper;
import com.ikitama.management_of_schools.mapper.StudentMapper;
import com.ikitama.management_of_schools.mapper.SubjectMapper;
import com.ikitama.management_of_schools.repository.ClassroomRepository;
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
public class ClassroomService {
    ClassroomRepository classroomRepository;
    ClassroomMapper classroomMapper;
    SubjectMapper subjectMapper;
    StudentMapper studentMapper;
    MessageSource messageSource;

    private static final Logger logger = LogManager.getLogger(ClassroomService.class);

    public Classroom createClassroom(Classroom classroom){

        classroomRepository.findByNameIgnoreCase(classroom.getName())
                .ifPresent(classroomEntity -> {
                    throw new RequestException(messageSource.getMessage("classroom.exists",new Object[]{classroom.getName()},
                            Locale.getDefault()), HttpStatus.CONFLICT);
                });

        return classroomMapper.toClassroom(classroomRepository.save(classroomMapper.fromClassroom(classroom)));
    }

    public Classroom updateClassroom(Classroom classroom){

        return classroomRepository.findById(classroom.getId()).map(classroomEntity -> classroomMapper.toClassroom(
                classroomRepository.save(classroomMapper.fromClassroom(classroom))
        )).orElseThrow(
                ()->
                    new EntityNotFoundException(messageSource.getMessage("classroom.notFound",new Object[]{classroom.getId()},
                            Locale.getDefault()))
        );

    }

    public Classroom getClassroom(Long ID){
        return classroomMapper.toClassroom(classroomRepository.findById(ID).orElseThrow(
                ()-> new EntityNotFoundException(messageSource.getMessage("classroom.notFound",new Object[]{ID},
                        Locale.getDefault()))
        ));
    }

    public List<Classroom> getClassrooms(){
        return classroomRepository.findAll().stream()
                .map(classroomMapper::toClassroom)
                .collect(Collectors.toList());
    }

    public void deleteClassroom(Long ID) {
        // Vérifier d'abord si le classroom avec cet ID existe
        if (classroomRepository.findById(ID).isPresent()) {
            try {
                classroomRepository.deleteById(ID);
                logger.info("Le classroom avec l'ID {} a été supprimé.", ID);

            } catch (Exception exception) {
                logger.error("Erreur lors de la suppression du classroom avec l'ID {} : {}", ID, exception.getMessage());

                throw new RequestException(
                        messageSource.getMessage("classroom.errorDeletion", new Object[]{ID},
                                Locale.getDefault()),
                        HttpStatus.CONFLICT
                );
            }
        } else {
            // Si le classroom n'existe pas, lancer une exception appropriée
            logger.warn("Tentative de suppression d'un classroom avec l'ID {} qui n'existe pas.", ID);

            throw new EntityNotFoundException(messageSource.getMessage("classroom.notFound", new Object[]{ID},
                    Locale.getDefault()));
        }
    }

    public List<Subject> getSubjectsInClassroom(Long classroomId) {
        ClassroomEntity classroomEntity = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("classroom.notFound",
                        new Object[]{classroomId}, Locale.getDefault())));

        return classroomEntity.getSubjects().stream()
                .map(subjectEntity -> {
                    logger.debug("Mapping subjectEntity to subject: {}", subjectEntity.getId());
                    return subjectMapper.toSubject(subjectEntity);
                })
                .collect(Collectors.toList());
    }

    public List<Student> getStudentsInClassroom(Long classroomId) {
        ClassroomEntity classroomEntity = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("classroom.notFound",
                        new Object[]{classroomId}, Locale.getDefault())));

        return classroomEntity.getStudents().stream()
                .map(studentEntity -> {
                    logger.debug("Mapping studentEntity to student: {}", studentEntity.getId());
                    return studentMapper.toStudent(studentEntity);
                })
                .collect(Collectors.toList());
    }

}
