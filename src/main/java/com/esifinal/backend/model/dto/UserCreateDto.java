package com.esifinal.backend.model.dto;

import lombok.Data;

@Data
public class UserCreateDto {
    private String username;
    private String password;
    private String role;
    private String name;
}
