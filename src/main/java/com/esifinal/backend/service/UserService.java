package com.esifinal.backend.service;

import com.esifinal.backend.model.User;
import com.esifinal.backend.model.dto.UserCreateDto;
import com.esifinal.backend.model.dto.UserDto;
import com.esifinal.backend.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    private final List<String> availableRoles = new ArrayList<>(
            Arrays.asList("ROLE_ADMIN", "ROLE_USER"));

    public UserDto createUser(UserCreateDto userCreateDto) {
        return objectMapper.convertValue(userRepository.save(User.builder()
                .name(userCreateDto.getName())
                .username(userCreateDto.getUsername())
                .password(passwordEncoder.encode(userCreateDto.getPassword()))
                .role(getRole(userCreateDto.getRole()))
                .build()), UserDto.class);
    }

    private String getRole(String role) {
        if (availableRoles.contains(role))
            return role;
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role does not exists");
    }
}
