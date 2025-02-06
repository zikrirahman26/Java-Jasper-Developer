package backendspringboot.com.service;

import backendspringboot.com.dto.UserRequest;
import backendspringboot.com.dto.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse addUser(UserRequest userRequest);

    UserResponse updateUser(UserRequest userRequest, Long userId);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long userId);

    void deleteUser(Long userId);
}
