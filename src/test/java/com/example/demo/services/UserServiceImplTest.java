package com.example.demo.services;

import com.example.demo.dto.UserCredentialsUpdateRequest;
import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserUpdateRequest;
import com.example.demo.enums.Country;
import com.example.demo.model.UserCredentials;
import com.example.demo.repository.UserCredentialsRepository;
import com.example.demo.testHelpers.TestUtilities;
import com.example.demo.testHelpers.TestUtilities.*;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserCredentialsRepository userCredentialsRepository;

    @InjectMocks
    private UserServiceImpl userService;

    User user = TestUtilities.buildUser();

    UserCredentials userCredentials = TestUtilities.buildUserCredentials();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_ReturnsSavedUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userCredentialsRepository.save(any(UserCredentials.class))).thenReturn(userCredentials);

        UserRequest request = TestUtilities.buildUserRequest();

        // Act
        var response = userService.createUser(request);

        // Assert
        assertNotNull(response);
        assertEquals("Juliana", response.getFirstName());
        assertEquals("Canada", response.getCountry());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getUserById_UserExists_ReturnsUser() {

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        var response = userService.getUserById(user.getId().toString());

        assertNotNull(response);
        assertEquals("Juliana", response.get().getFirstName());
        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    void getUserById_UserDoesNotExist_ReturnsEmpty() {

        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        var response = userService.getUserById(user.getId().toString());
        assertEquals(Optional.empty(), response);
        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    void getUserCredentials_UserExists_ReturnsUserCredentials() {

        when(userCredentialsRepository.findById(userCredentials.getId())).thenReturn(Optional.of(userCredentials));

        var response = userService.getUserCredentials(userCredentials.getId().toString());

        assertNotNull(response);
        assertEquals(userCredentials.getUsername(), response.get().getUsername());
        verify(userCredentialsRepository, times(1)).findById(userCredentials.getId());
    }

    @Test
    void getUserCredentials_UserDoesNotExist_ReturnsEmpty() {

        when(userCredentialsRepository.findById(userCredentials.getId())).thenReturn(Optional.empty());

        var response = userService.getUserCredentials(userCredentials.getId().toString());

        assertNotNull(response);
        assertEquals(Optional.empty(), response);
        verify(userCredentialsRepository, times(1)).findById(userCredentials.getId());
    }

    //Update address only for user
    @Test
    void updateUser_ReturnsUpdatedUser() {
        UserUpdateRequest userUpdateRequest = TestUtilities.buildUserUpdateRequest();

        //Update address
        User updatedUser = User.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(userUpdateRequest.getAddress())
                .country(user.getCountry())
                .build();

        when(userRepository.findById(UUID.fromString(userUpdateRequest.getId()))).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        var response = userService.updateUser(userUpdateRequest.getId(), userUpdateRequest, user.getUserCredentials().getUsername());

        assertNotNull(response);
        assertEquals("Juliana", response.getFirstName());
        assertEquals("456 New Street", response.getAddress());
        verify(userRepository, times(1)).findById(UUID.fromString(userUpdateRequest.getId()));
    }

    //Update all fields for code coverage
    @Test
    void updateAllFieldsForUser_ReturnsUpdatedUser() {
        UserUpdateRequest userUpdateRequest = UserUpdateRequest.builder()
                .id(user.getId().toString())
                .firstName("New First Name")
                .lastName("New LastName")
                .email("newEmail@test.com")
                .phone(987654321)
                .address("456 New Street")
                .country(Country.United_States.toString())
                .build();


        //Update address
        User updatedUser = User.builder()
                .id(UUID.fromString(userUpdateRequest.getId()))
                .firstName(userUpdateRequest.getFirstName())
                .lastName(userUpdateRequest.getLastName())
                .email(userUpdateRequest.getEmail())
                .phone(userUpdateRequest.getPhone())
                .address(userUpdateRequest.getAddress())
                .country(Country.valueOf(userUpdateRequest.getCountry()))
                .build();

        when(userRepository.findById(UUID.fromString(userUpdateRequest.getId()))).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        var response = userService.updateUser(userUpdateRequest.getId(), userUpdateRequest, user.getUserCredentials().getUsername());

        assertNotNull(response);
        assertEquals(userUpdateRequest.getFirstName(), response.getFirstName());
        assertEquals(userUpdateRequest.getAddress(), response.getAddress());
        verify(userRepository, times(1)).findById(UUID.fromString(userUpdateRequest.getId()));
    }

    @Test
    void updateUser_UserNotFound_ReturnsException() {
        UserUpdateRequest userUpdateRequest = TestUtilities.buildUserUpdateRequest();

        RuntimeException ex = assertThrows(UserNotFoundException.class, () -> userService.updateUser(userUpdateRequest.getId(), userUpdateRequest, user.getUserCredentials().getUsername()));

        assertEquals("User {} " + userUpdateRequest.getId() + " not found.", ex.getMessage());
        verify(userRepository, times(1)).findById(UUID.fromString(userUpdateRequest.getId()));
        verify(userRepository, never()).save(any());
    }

    @Test
    void updateUserCredentials_ReturnsUpdatedUserCredentials() {
        UserCredentialsUpdateRequest userCredentialsUpdateRequest = TestUtilities.buildUserCredentialsUpdateRequest();
        userCredentialsUpdateRequest.setUsername("newUsername");

        //Update address
        User updatedUser = User.builder()
                .id(UUID.fromString(userCredentialsUpdateRequest.getId()))
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .country(user.getCountry())
                .userCredentials(userCredentials)
                .build();

        UserCredentials userCredentialsUpdated = UserCredentials.builder()
                .id(updatedUser.getId())
                .username(userCredentialsUpdateRequest.getUsername())
                .password(userCredentialsUpdateRequest.getPassword()).
                build();

        when(userRepository.findById(UUID.fromString(userCredentialsUpdateRequest.getId()))).thenReturn(Optional.of(user));
        when(userCredentialsRepository.save(any(UserCredentials.class))).thenReturn(userCredentialsUpdated);
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        var response = userService.updateUserCredentials(userCredentialsUpdateRequest);

        assertNotNull(response);
        assertEquals(userCredentialsUpdateRequest.getUsername(), response.getUsername());
        verify(userRepository, times(1)).findById(UUID.fromString(userCredentialsUpdateRequest.getId()));
    }

    //For code coverage
    @Test
    void updateUserCredentials_OnlyPassword_ReturnsUpdatedUserCredentials() {
        UserCredentialsUpdateRequest userCredentialsUpdateRequest = TestUtilities.buildUserCredentialsUpdateRequest();
        userCredentialsUpdateRequest.setPassword("newPassword");

        //Update address
        User updatedUser = User.builder()
                .id(UUID.fromString(userCredentialsUpdateRequest.getId()))
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .country(user.getCountry())
                .userCredentials(userCredentials)
                .build();

        UserCredentials userCredentialsUpdated = UserCredentials.builder()
                .id(updatedUser.getId())
                .username(userCredentialsUpdateRequest.getUsername())
                .password(userCredentialsUpdateRequest.getPassword()).
                build();

        when(userRepository.findById(UUID.fromString(userCredentialsUpdateRequest.getId()))).thenReturn(Optional.of(user));
        when(userCredentialsRepository.save(any(UserCredentials.class))).thenReturn(userCredentialsUpdated);
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        var response = userService.updateUserCredentials(userCredentialsUpdateRequest);

        assertNotNull(response);
        assertEquals(userCredentialsUpdateRequest.getUsername(), response.getUsername());
        verify(userRepository, times(1)).findById(UUID.fromString(userCredentialsUpdateRequest.getId()));
    }

    @Test
    void updateUserCredentials_UserIsNotFound_ReturnsEmpty() {
        UserCredentialsUpdateRequest userCredentialsUpdateRequest = TestUtilities.buildUserCredentialsUpdateRequest();
        userCredentialsUpdateRequest.setPassword("newPassword");

        RuntimeException ex = assertThrows(UserNotFoundException.class, () -> userService.updateUserCredentials(userCredentialsUpdateRequest));

        assertEquals("User {} " + userCredentialsUpdateRequest.getId() + " not found.", ex.getMessage());
        verify(userRepository, times(1)).findById(UUID.fromString(userCredentialsUpdateRequest.getId()));
        verify(userRepository, never()).save(any());
    }

    @Test
    void deleteUser() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));

        userService.deleteUser(String.valueOf(user.getId()));

        verify(userRepository, times(1)).findById(user.getId());
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void deleteUser_UserNotFound_ReturnsException() {
        RuntimeException ex = assertThrows(UserNotFoundException.class, () -> userService.deleteUser(String.valueOf(user.getId())));

        assertEquals("User {} " + user.getId() + " not found.", ex.getMessage());
        verify(userRepository, times(1)).findById(UUID.fromString(String.valueOf(user.getId())));
        verify(userRepository, never()).save(any());
    }

}

