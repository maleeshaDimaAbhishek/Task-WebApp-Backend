package edu.maleesha.task_management.model.DTO;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
public class UserRequestDTO {
    private String username;
    private String name;
    private LocalDate birthDate;
    private String email;
    private String password;
}
