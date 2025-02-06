package backendspringboot.com.controller;

import backendspringboot.com.dto.ApiResponse;
import backendspringboot.com.dto.UserRequest;
import backendspringboot.com.dto.UserResponse;
import backendspringboot.com.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> addUser(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.addUser(userRequest);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<UserResponse>builder()
                        .data(userResponse)
                        .message("success add user")
                .build());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable Long userId, @RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.updateUser(userRequest, userId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<UserResponse>builder()
                        .data(userResponse)
                        .message("success update user")
                .build());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long userId) {
        UserResponse userResponse = userService.getUserById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<UserResponse>builder()
                        .data(userResponse)
                        .message("success get user")
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Object>> getAllUsers() {
        List<UserResponse> response = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder()
                        .data(response)
                        .message("success get users")
                .build());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Object>> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder()
                        .data(null)
                        .message("success delete user")
                .build());
    }
}
