package edu.maleesha.task_management.controller;

import edu.maleesha.task_management.model.DTO.AuthResponseDTO;
import edu.maleesha.task_management.model.DTO.LoginRequestDTO;
import edu.maleesha.task_management.model.DTO.UserRequestDTO;
import edu.maleesha.task_management.model.DTO.UserResponseDTO;
import edu.maleesha.task_management.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    //User Registration
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO>registerUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO createUser=userService.createUser(userRequestDTO);
        return new ResponseEntity<>(createUser, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(userService.login(loginRequestDTO));
    }
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userRequestDTO));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
