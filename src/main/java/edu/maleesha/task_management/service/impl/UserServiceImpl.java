package edu.maleesha.task_management.service.impl;

import edu.maleesha.task_management.exception.ResourceAlreadyExistsException;
import edu.maleesha.task_management.model.DTO.UserRequestDTO;
import edu.maleesha.task_management.model.DTO.UserResponseDTO;
import edu.maleesha.task_management.model.entity.User;
import edu.maleesha.task_management.repository.UserRepository;
import edu.maleesha.task_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        userRepository.findByUsername(userRequestDTO.getUsername())
                .ifPresent(user -> {
                    throw new ResourceAlreadyExistsException("Username already exists");
                });
        userRepository.findByEmail(userRequestDTO.getEmail())
                .ifPresent(user -> {
                    throw new ResourceAlreadyExistsException("Email already exists");
                });
        User user = modelMapper.map(userRequestDTO, User.class);
        return modelMapper.map(userRepository.save(user), UserResponseDTO.class);
    }
}
