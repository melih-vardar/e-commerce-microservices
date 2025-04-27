package com.melvard.user_service.service;

import java.util.List;
import java.util.UUID;

import io.github.melihvardar.dtos.user.JwtResponseDTO;
import io.github.melihvardar.dtos.user.UserRegisterDTO;
import io.github.melihvardar.dtos.user.UserResponseDTO;
import io.github.melihvardar.dtos.user.UserLoginDTO;

public interface UserService {

    UserResponseDTO getUserById(UUID id);

    UserResponseDTO getUserByEmail(String email);

    UserResponseDTO updateUser(UUID id, UserRegisterDTO userRegisterDTO);

    void deleteUser(UUID id);

    JwtResponseDTO register(UserRegisterDTO userRegisterDTO);

    JwtResponseDTO login(UserLoginDTO userLoginDTO);

    List<UserResponseDTO> getAllUsers();
}
