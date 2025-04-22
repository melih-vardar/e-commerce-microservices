package com.melvard.user_service.service;

import com.melvard.user_service.entity.User;
import com.melvard.user_service.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import io.github.melihvardar.dtos.user.UserResponseDTO;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public UserResponseDTO getUserById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public UserResponseDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
    }
}
