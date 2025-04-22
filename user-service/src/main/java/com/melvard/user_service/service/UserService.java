package com.melvard.user_service.service;

import java.util.UUID;

import io.github.melihvardar.dtos.user.UserResponseDTO;

public interface UserService {

    UserResponseDTO getUserById(UUID id);

    UserResponseDTO getUserByEmail(String email);
}
