package com.melvard.user_service.rules;

import com.melvard.user_service.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserBusinessRules {

    private final UserRepository userRepository;

    public void checkIfUserExistsByEmail(String email) {
        if (userRepository.findByEmail(email) == null) {
            throw new BusinessRuleException("User with email " + email + " does not exist.");
        }
    }
}
