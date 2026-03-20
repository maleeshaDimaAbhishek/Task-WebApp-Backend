package edu.maleesha.task_management.service.impl;

import edu.maleesha.task_management.exception.ResourceAlreadyExistsException;
import edu.maleesha.task_management.exception.ResourceNotFoundException;
import edu.maleesha.task_management.model.DTO.AuthResponseDTO;
import edu.maleesha.task_management.model.DTO.LoginRequestDTO;
import edu.maleesha.task_management.model.DTO.UserRequestDTO;
import edu.maleesha.task_management.model.DTO.UserResponseDTO;
import edu.maleesha.task_management.model.entity.User;
import edu.maleesha.task_management.repository.UserRepository;
import edu.maleesha.task_management.service.UserService;
import edu.maleesha.task_management.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
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
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        return modelMapper.map(userRepository.save(user), UserResponseDTO.class);
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO loginRequestDTO) {
        User user=userRepository.findByUsername(loginRequestDTO.getUsername())
                .orElseThrow(()->new RuntimeException("Invalid username"));
        if(!passwordEncoder.matches(loginRequestDTO.getPassword(),user.getPassword())){
            throw new RuntimeException("Invalid username or password");
        }
        String token=jwtUtil.generateToken(user.getUsername());
        return new AuthResponseDTO(token);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserResponseDTO.class))
                .toList();
    }

    @Override
    public @Nullable UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with ID: " + id));
        return modelMapper.map(user, UserResponseDTO.class);
    }

    @Override
    public @Nullable UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with ID: " + id));

        if (userRequestDTO.getUsername() != null) {
            user.setUsername(userRequestDTO.getUsername());
        }
        if (userRequestDTO.getName() != null) {
            user.setName(userRequestDTO.getName());
        }
        if (userRequestDTO.getBirthDate() != null) {
            user.setBirthDate(userRequestDTO.getBirthDate());
        }
        if (userRequestDTO.getStatus() != null) {
            user.setStatus(userRequestDTO.getStatus());
        }
        if (userRequestDTO.getEmail() != null) {
            user.setEmail(userRequestDTO.getEmail());
        }
        if (userRequestDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        }
        return modelMapper.map(userRepository.save(user), UserResponseDTO.class);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with ID: " + id));
        userRepository.delete(user);
    }
}
