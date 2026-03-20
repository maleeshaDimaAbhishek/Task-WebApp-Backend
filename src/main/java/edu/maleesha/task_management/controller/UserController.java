package edu.maleesha.task_management.controller;

import edu.maleesha.task_management.model.DTO.UserRequestDTO;
import edu.maleesha.task_management.model.DTO.UserResponseDTO;
import edu.maleesha.task_management.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ai/auth")
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
}
