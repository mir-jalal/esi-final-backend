package com.esifinal.backend.model.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String name;
    private String role;
}
