package com.melvard.user_service.service;

import java.util.UUID;

import com.melvard.user_service.dtos.JwtResponseDTO;
import com.melvard.user_service.dtos.UserRegisterDTO;
import com.melvard.user_service.dtos.UserResponseDTO;

public interface UserService {

    UserResponseDTO getUserById(UUID id);

    UserResponseDTO getUserByEmail(String email);

    UserResponseDTO updateUser(UUID id, UserRegisterDTO userRegisterDTO);

    void deleteUser(UUID id);

    JwtResponseDTO register(UserRegisterDTO userRegisterDTO);
}
