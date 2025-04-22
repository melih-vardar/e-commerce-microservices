package com.melvard.user_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponseDTO {
    private String token;
    private UserResponseDTO user;
    private List<String> roles;
}
