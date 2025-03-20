package com.ikitama.management_of_schools.service;

import com.ikitama.management_of_schools.domain.Grade;
import com.ikitama.management_of_schools.exception.EntityNotFoundException;
import com.ikitama.management_of_schools.exception.RequestException;
import com.ikitama.management_of_schools.mapper.GradeMapper;
import com.ikitama.management_of_schools.repository.GradeRepository;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GradeService {
    GradeRepository gradeRepository;
    GradeMapper gradeMapper;
    MessageSource messageSource;

    private static final Logger logger = LogManager.getLogger(GradeService.class);

    public Grade createGrade(Grade grade){
        return gradeMapper.toGrade(gradeRepository.save(gradeMapper.fromGrade(grade)));
    }

    public Grade updateGrade(Grade grade){

        return gradeRepository.findById(grade.getId()).map(studentEntity -> gradeMapper.toGrade(
                gradeRepository.save(gradeMapper.fromGrade(grade))
        )).orElseThrow(
                ()->
                        new EntityNotFoundException(messageSource.getMessage("grade.notFound",new Object[]{grade.getId()},
                                Locale.getDefault()))
        );
    }

    public Grade getGrade(Long ID){

        return gradeMapper.toGrade(gradeRepository.findById(ID).orElseThrow(
                ()-> new EntityNotFoundException(messageSource.getMessage("grade.notFound",new Object[]{ID},
                        Locale.getDefault()))
        ));
    }

    public List<Grade> getGrades(){

        return gradeRepository.findAll().stream()
                .map(gradeMapper::toGrade)
                .collect(Collectors.toList());
    }

    public void deleteGrade(Long ID){

        // Vérifier d'abord si la grade avec cet ID existe
        if (gradeRepository.findById(ID).isPresent()) {
            try {
                gradeRepository.deleteById(ID);
                logger.info("Le grade avec l'ID {} a été supprimé.", ID);

            } catch (Exception exception) {
                logger.error("Erreur lors de la suppression de la grade avec l'ID {} : {}", ID, exception.getMessage());

                throw new RequestException(
                        messageSource.getMessage("note.errorDeletion", new Object[]{ID},
                                Locale.getDefault()),
                        HttpStatus.CONFLICT
                );
            }
        } else {
            // Si la note n'existe pas, lancer une exception appropriée
            logger.warn("Tentative de suppression d'une note note avec l'ID {} qui n'existe pas.", ID);

            throw new EntityNotFoundException(messageSource.getMessage("grade.notFound", new Object[]{ID},
                    Locale.getDefault()));
        }
    }
}
