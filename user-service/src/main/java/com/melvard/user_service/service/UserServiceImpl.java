package com.melvard.user_service.service;

import com.melvard.user_service.entity.User;
import com.melvard.user_service.repository.UserRepository;
import com.melvard.user_service.rules.UserBusinessRules;
import io.github.melihvardar.dtos.user.JwtResponseDTO;
import io.github.melihvardar.dtos.user.UserLoginDTO;
import io.github.melihvardar.dtos.user.UserRegisterDTO;
import com.melvard.user_service.security.JwtService;
import io.github.melihvardar.enums.Role;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import io.github.melihvardar.dtos.user.UserResponseDTO;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserBusinessRules businessRules;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public UserResponseDTO getUserById(UUID id) {
        businessRules.checkIfUserExistsById(id);
        User user = userRepository.findById(id).get();
        return userResponseDTOBuilder(user);
    }

    @Override
    public UserResponseDTO getUserByEmail(String email) {
        businessRules.checkIfUserExistsByEmail(email);
        User user = userRepository.findByEmail(email);
        return userResponseDTOBuilder(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::userResponseDTOBuilder)
                .collect(Collectors.toList());
    }

    @Override
    public JwtResponseDTO register(UserRegisterDTO userRegisterDTO) {
        businessRules.checkIfUserExistsByEmail(userRegisterDTO.getEmail());

        User user = User.builder()
                .email(userRegisterDTO.getEmail())
                .password(passwordEncoder.encode(userRegisterDTO.getPassword()))
                .role(userRegisterDTO.getRole() != null ? userRegisterDTO.getRole() : Role.USER)
                .build();

        userRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        userRegisterDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateTokenWithRoles(authentication, user.getId());

        UserResponseDTO userResponseDTO = userResponseDTOBuilder(user);

        List<String> roles = jwtService.extractRoles(jwt);

        return JwtResponseDTO.builder()
                .token(jwt)
                .user(userResponseDTO)
                .roles(roles)
                .build();
    }

    @Override
    public JwtResponseDTO login(UserLoginDTO userLoginDTO) {
        businessRules.checkIfUserExistsByEmail(userLoginDTO.getEmail());

        User user = userRepository.findByEmail(userLoginDTO.getEmail());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        userLoginDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.generateTokenWithRoles(authentication, user.getId());

        UserResponseDTO userResponseDTO = userResponseDTOBuilder(user);

        List<String> roles = jwtService.extractRoles(jwt);

        return JwtResponseDTO.builder()
                .token(jwt)
                .user(userResponseDTO)
                .roles(roles)
                .build();
    }

    @Override
    public UserResponseDTO updateUser(UUID id, UserRegisterDTO userRegisterDTO) {
        businessRules.checkIfUserExistsById(id);
        businessRules.checkIfEmailExists(userRegisterDTO.getEmail());

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
        businessRules.checkIfUserExistsById(id);

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
