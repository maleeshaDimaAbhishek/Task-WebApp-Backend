package edu.maleesha.task_management.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "UserName Is Required")
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String name;
    @NotBlank(message = "Birth Date is Required")
    @Column(nullable = false)
    private LocalDate birthDate;
    @Email(message = "Please provide a valid email")
    @NotBlank(message = "Email is Required")
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String status;
    @NotBlank(message = "Password is Required")
    @Size(min = 8,message = "Password must be at least 8 characters")
    @Column(nullable = false)
    private String password;

}
