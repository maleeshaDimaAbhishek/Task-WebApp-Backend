package edu.maleesha.task_management.service;

import edu.maleesha.task_management.model.DTO.LoginRequestDTO;
import edu.maleesha.task_management.model.DTO.UserRequestDTO;
import edu.maleesha.task_management.model.DTO.UserResponseDTO;
import jakarta.validation.Valid;

public interface UserService {
    UserResponseDTO createUser(@Valid UserRequestDTO userRequestDTO);

    UserResponseDTO login(@Valid LoginRequestDTO loginRequestDTO);
}
