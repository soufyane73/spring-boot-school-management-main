package com.ikitama.management_of_schools.service;

import com.ikitama.management_of_schools.domain.User;
import com.ikitama.management_of_schools.entity.UserEntity;
import com.ikitama.management_of_schools.exception.EntityNotFoundException;
import com.ikitama.management_of_schools.exception.RequestException;
import com.ikitama.management_of_schools.mapper.UserMapper;
import com.ikitama.management_of_schools.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    MessageSource messageSource;
    PasswordEncoder passwordEncoder;

    private static final Logger logger = LogManager.getLogger(StudentService.class);

    public User updateUser(User user){

        return userRepository.findById(user.getId()).map(subjectEntity -> userMapper.toUser(
                userRepository.save(userMapper.fromUser(user))
        )).orElseThrow(
                ()->
                        new EntityNotFoundException(messageSource.getMessage("user.notFound",new Object[]{user.getId()},
                                Locale.getDefault()))
        );
    }

    public User changePassword(Long userId, String newPassword) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(messageSource.getMessage("user.notFound", new Object[]{userId},
                        Locale.getDefault()))
        );

        // Encode le nouveau mot de passe
        userEntity.setPassword(passwordEncoder.encode(newPassword));

        UserEntity updatedUserEntity = userRepository.save(userEntity);

        return userMapper.toUser(updatedUserEntity);
    }

    public User getUser(Long ID){

        return userMapper.toUser(userRepository.findById(ID).orElseThrow(
                ()-> new EntityNotFoundException(messageSource.getMessage("user.notFound",new Object[]{ID},
                        Locale.getDefault()))
        ));
    }

    public List<User> getUsers(){

        return userRepository.findAll().stream()
                .map(userMapper::toUser)
                .collect(Collectors.toList());
    }

    public User getUserByEmail(String email){

        return userMapper.toUser(userRepository.findByEmail(email).orElseThrow(
                ()-> new EntityNotFoundException(messageSource.getMessage("user.notFoundByEmail",new Object[]{email},
                        Locale.getDefault()))
        ));
    }

    public User getUserByName(String name) {
        return userMapper.toUser(userRepository.findByName(name).orElseThrow(
                () -> new EntityNotFoundException(messageSource.getMessage("user.notFoundByName", new Object[]{name},
                        Locale.getDefault()))
        ));
    }

    public void deleteUser(Long ID){

// Vérifier d'abord si le user avec cet ID existe
        if (userRepository.findById(ID).isPresent()) {
            try {
                userRepository.deleteById(ID);
                logger.info("Le user avec l'ID {} a été supprimé.", ID);

            } catch (Exception exception) {
                logger.error("Erreur lors de la suppression du user avec l'ID {} : {}", ID, exception.getMessage());

                throw new RequestException(
                        messageSource.getMessage("user.errorDeletion", new Object[]{ID},
                                Locale.getDefault()),
                        HttpStatus.CONFLICT
                );
            }
        } else {
            // Si le user n'existe pas, lancer une exception appropriée
            logger.warn("Tentative de suppression d'un user avec l'ID {} qui n'existe pas.", ID);

            throw new EntityNotFoundException(messageSource.getMessage("user.notFound", new Object[]{ID},
                    Locale.getDefault()));
        }
    }
}