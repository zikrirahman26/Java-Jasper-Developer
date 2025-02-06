package backendspringboot.com;

import backendspringboot.com.dto.UserRequest;
import backendspringboot.com.dto.UserResponse;
import backendspringboot.com.entity.User;
import backendspringboot.com.repository.UserRepository;
import backendspringboot.com.service.ValidationService;
import backendspringboot.com.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ValidationService validationService;

    private User user;
    private UserRequest userRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Inisialisasi mock
        user = new User(1L, "John Doe", "john@example.com", "123456789");
        userRequest = new UserRequest("John Doe", "john@example.com", "123456789");
    }

    @Test
    public void testAddUser() {
        doNothing().when(validationService).validation(userRequest);

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponse response = userService.addUser(userRequest);

        assertNotNull(response);
        assertEquals("John Doe", response.getName());
        assertEquals("john@example.com", response.getEmail());
        assertEquals("123456789", response.getPhone());
    }

    @Test
    public void testUpdateUser_Success() {
        doNothing().when(validationService).validation(userRequest);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponse response = userService.updateUser(userRequest, 1L);

        assertNotNull(response);
        assertEquals("John Doe", response.getName());
        assertEquals("john@example.com", response.getEmail());
        assertEquals("123456789", response.getPhone());
    }

    @Test
    public void testUpdateUser_NotFound() {
        doNothing().when(validationService).validation(userRequest);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userService.updateUser(userRequest, 1L);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("user not found", exception.getReason());
    }

    @Test
    public void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        List<UserResponse> responseList = userService.getAllUsers();

        assertNotNull(responseList);
        assertEquals(1, responseList.size());
        assertEquals("John Doe", responseList.get(0).getName());
    }

    @Test
    public void testGetUserById_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserResponse response = userService.getUserById(1L);

        assertNotNull(response);
        assertEquals("John Doe", response.getName());
        assertEquals("john@example.com", response.getEmail());
        assertEquals("123456789", response.getPhone());
    }

    @Test
    public void testGetUserById_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userService.getUserById(1L);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("user not found", exception.getReason());
    }

    @Test
    public void testDeleteUser_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteUser_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userService.deleteUser(1L);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("user not found", exception.getReason());
    }
}
