package com.melvard.user_service.service;

import com.melvard.user_service.entity.User;
import com.melvard.user_service.repository.UserRepository;
import com.melvard.user_service.rules.UserBusinessRules;
import com.melvard.user_service.dtos.JwtResponseDTO;
import com.melvard.user_service.dtos.UserRegisterDTO;
import io.github.melihvardar.enums.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.melvard.user_service.dtos.UserResponseDTO;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserBusinessRules userBusinessRules;

    @Override
    public UserResponseDTO getUserById(UUID id) {
        userBusinessRules.checkIfUserExistsById(id);
        User user = userRepository.findById(id).get();
        return userResponseDTOBuilder(user);
    }

    @Override
    public UserResponseDTO getUserByEmail(String email) {
        userBusinessRules.checkIfUserExistsByEmail(email);
        User user = userRepository.findByEmail(email);
        return userResponseDTOBuilder(user);
    }

    @Override
    public JwtResponseDTO register(UserRegisterDTO userRegisterDTO) {
        userBusinessRules.checkIfUserExistsByEmail(userRegisterDTO.getEmail());

        User user = User.builder()
                .email(userRegisterDTO.getEmail())
                .password(userRegisterDTO.getPassword())
                .role(userRegisterDTO.getRole() != null ? userRegisterDTO.getRole() : Role.USER)
                .build();

        userRepository.save(user);

        List<String> randomList = new ArrayList<>();

        UserResponseDTO userResponseDTO = userResponseDTOBuilder(user);
        return JwtResponseDTO.builder()
                .token("randomjwt")
                .user(userResponseDTO)
                .roles(randomList)
                .build();
    }

    @Override
    public UserResponseDTO updateUser(UUID id, UserRegisterDTO userRegisterDTO) {
        userBusinessRules.checkIfUserExistsById(id);
        userBusinessRules.checkIfEmailExists(userRegisterDTO.getEmail());

        User user = userRepository.findById(id).get();

        user.setEmail(userRegisterDTO.getEmail());
        if (!user.getPassword().equals(userRegisterDTO.getPassword())) {
            user.setPassword(userRegisterDTO.getPassword());
        }

        userRepository.save(user);

        return userResponseDTOBuilder(user);
    }

    @Override
    public void deleteUser(UUID id) {
        userBusinessRules.checkIfUserExistsById(id);

        User user = userRepository.findById(id).get();
        userRepository.delete(user);
    }

    public UserResponseDTO userResponseDTOBuilder(User user){
        return UserResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
