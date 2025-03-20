package com.ikitama.management_of_schools.service;

import com.ikitama.management_of_schools.domain.Classroom;
import com.ikitama.management_of_schools.domain.Grade;
import com.ikitama.management_of_schools.domain.Student;
import com.ikitama.management_of_schools.entity.StudentEntity;
import com.ikitama.management_of_schools.exception.EntityNotFoundException;
import com.ikitama.management_of_schools.exception.RequestException;
import com.ikitama.management_of_schools.mapper.ClassroomMapper;
import com.ikitama.management_of_schools.mapper.GradeMapper;
import com.ikitama.management_of_schools.mapper.StudentMapper;
import com.ikitama.management_of_schools.repository.StudentRepository;
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
public class StudentService {

    StudentRepository studentRepository;

    StudentMapper studentMapper;
    GradeMapper gradeMapper;
    ClassroomMapper classroomMapper;

    ClassroomService classroomService;

    MessageSource messageSource;

    private static final Logger logger = LogManager.getLogger(StudentService.class);

   public Student createStudent(Student student){

       Classroom classroom= classroomService.getClassroom(student.getClassroom().getId());
       List<Student> classrooms= classroomService.getStudentsInClassroom(classroom.getId());

       if (classroom != null && classrooms.size() < classroom.getMaximum_capacity()) {
           // Le nombre d'étudiants dans la classe est inférieur à maximum_capacity, enregistrez l'étudiant
           return studentMapper.toStudent(studentRepository.save(studentMapper.fromStudent(student)));
       } else {
           // Le nombre maximum d'étudiants dans la classe a été atteint
           throw new RequestException(
                   messageSource.getMessage("classroom.maximumCapacityReached", null, Locale.getDefault()),
                   HttpStatus.BAD_REQUEST
           );
       }
   }

    public Student updateStudent(Student student){

        return studentRepository.findById(student.getId()).map(studentEntity -> studentMapper.toStudent(
                studentRepository.save(studentMapper.fromStudent(student))
        )).orElseThrow(
                ()->
                        new EntityNotFoundException(messageSource.getMessage("student.notFound",new Object[]{student.getId()},
                                Locale.getDefault()))
        );
    }

    public Student getStudent(Long ID){
        return studentMapper.toStudent(studentRepository.findById(ID).orElseThrow(
                ()-> new EntityNotFoundException(messageSource.getMessage("student.notFound",new Object[]{ID},
                        Locale.getDefault()))
        ));
    }

    public List<Student> getStudents(){
        return studentRepository.findAll().stream()
                .map(studentMapper::toStudent)
                .collect(Collectors.toList());
    }

    public void deleteStudent(Long ID) {
        // Vérifier d'abord si le student avec cet ID existe
        if (studentRepository.findById(ID).isPresent()) {
            try {
                studentRepository.deleteById(ID);
                logger.info("Le student avec l'ID {} a été supprimé.", ID);

            } catch (Exception exception) {
                logger.error("Erreur lors de la suppression du student avec l'ID {} : {}", ID, exception.getMessage());

                throw new RequestException(
                        messageSource.getMessage("student.errorDeletion", new Object[]{ID},
                                Locale.getDefault()),
                        HttpStatus.CONFLICT
                );
            }
        } else {
            // Si le student n'existe pas, lancer une exception appropriée
            logger.warn("Tentative de suppression d'un student avec l'ID {} qui n'existe pas.", ID);

            throw new EntityNotFoundException(messageSource.getMessage("student.notFound", new Object[]{ID},
                    Locale.getDefault()));
        }
    }

    public List<Grade> getGradesOfStudent(Long studentId){

        StudentEntity studentEntity=studentRepository.findById(studentId)
                .orElseThrow(()->new EntityNotFoundException(messageSource.getMessage("student.notFound",
                        new Object[]{studentId},Locale.getDefault())));

        return studentEntity.getGrades().stream()
                .map(gradeEntity -> {
                    logger.debug("Mapping gradeEntity to grade {}",gradeEntity.getId());

                    return gradeMapper.toGrade(gradeEntity);
                })
                .collect(Collectors.toList());

    }

    public Classroom getStudentClassroom(Long studentId) {
        StudentEntity studentEntity = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("student.notFound",
                        new Object[]{studentId}, Locale.getDefault())));

        return classroomMapper.toClassroom(studentEntity.getClassroom()) ;
    }

    public boolean checkRegistrationNumberExists(String registration_number) {
        return studentRepository.existsByRegistrationNumber(registration_number);
    }


}
