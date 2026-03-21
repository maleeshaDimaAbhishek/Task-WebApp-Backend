package edu.maleesha.task_management.service;

import edu.maleesha.task_management.model.DTO.AuthResponseDTO;
import edu.maleesha.task_management.model.DTO.LoginRequestDTO;
import edu.maleesha.task_management.model.DTO.UserRequestDTO;
import edu.maleesha.task_management.model.DTO.UserResponseDTO;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface UserService {
    UserResponseDTO createUser(@Valid UserRequestDTO userRequestDTO);

    AuthResponseDTO login(@Valid LoginRequestDTO loginRequestDTO);

    List<UserResponseDTO> getAllUsers();

    @Nullable UserResponseDTO getCurrentUser();

    @Nullable UserResponseDTO getUserById(Long id);

    @Nullable UserResponseDTO updateUser(Long id, @Valid UserRequestDTO userRequestDTO);

    void deleteUser(Long id);
}
