package com.bugpilot.service;

import com.bugpilot.dto.UserRequest;
import com.bugpilot.dto.UserResponse;
import com.bugpilot.entity.User;
import com.bugpilot.exception.UserNotFoundException;
import com.bugpilot.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Create User
    public UserResponse createUser(UserRequest userRequest) {

        User user = new User();

        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());

        User savedUser = userRepository.save(user);

        UserResponse response = new UserResponse();
        response.setId(savedUser.getId());
        response.setName(savedUser.getName());
        response.setEmail(savedUser.getEmail());

        return response;
    }

    // Get All Users
    public List<UserResponse> getAllUsers() {

        List<User> users = userRepository.findAll();
        List<UserResponse> responses = new ArrayList<>();

        for (User user : users) {

            UserResponse response = new UserResponse();

            response.setId(user.getId());
            response.setName(user.getName());
            response.setEmail(user.getEmail());

            responses.add(response);
        }

        return responses;
    }

    // Get User By Id
    public UserResponse getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());

        return response;
    }

    // Update User
    public UserResponse updateUser(Long id, UserRequest userRequest) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        existingUser.setName(userRequest.getName());
        existingUser.setEmail(userRequest.getEmail());

        User savedUser = userRepository.save(existingUser);

        UserResponse response = new UserResponse();

        response.setId(savedUser.getId());
        response.setName(savedUser.getName());
        response.setEmail(savedUser.getEmail());

        return response;
    }

    // Delete User
    public void deleteUser(Long id) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        userRepository.delete(existingUser);
    }
}