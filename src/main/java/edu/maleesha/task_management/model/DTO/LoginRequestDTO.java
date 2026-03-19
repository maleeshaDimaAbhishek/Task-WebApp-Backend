package edu.maleesha.task_management.model.DTO;

import jakarta.validation.constraints.NotBlank;

public class LoginRequestDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
