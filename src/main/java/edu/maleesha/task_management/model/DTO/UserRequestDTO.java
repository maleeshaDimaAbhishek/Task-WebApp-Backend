package edu.maleesha.task_management.model.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    @NotBlank
    private String username;
    private String name;
    @NotNull
    @Past
    private LocalDate birthDate;
    private String status;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Size(min = 8)
    private String password;
}
