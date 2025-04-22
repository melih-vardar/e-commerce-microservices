package com.melvard.user_service.rules;

import com.melvard.user_service.repository.UserRepository;
import io.github.melihvardar.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class UserBusinessRules {

    private final UserRepository userRepository;

    public void checkIfUserExistsByEmail(String email) {
        if (!userRepository.existsByEmail(email)) {
            throw new BusinessException("User with email " + email + " does not exist.");
        }
    }

    public void checkIfEmailExists(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new BusinessException("Email is already in use: " + email);
        }
    }

    public void checkIfUserExistsById(UUID id){
        if (!userRepository.existsById(id)){
            throw new BusinessException("User with this id does not exist");
        }
    }
}
