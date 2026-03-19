package edu.maleesha.task_management.model.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    @NotBlank(message = "Username is  required")
    private String username;
    private String name;
    @NotBlank(message = "Birthdate is Required")
    private LocalDate birthDate;
    private String status;
    @Email(message = "Invalid Email")
    @NotBlank(message = "Email is  required")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
}
