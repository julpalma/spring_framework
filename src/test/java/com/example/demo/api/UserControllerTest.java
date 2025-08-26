package com.example.demo.api;

import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.dto.UserUpdateRequest;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.services.UserServiceInterface;
import com.example.demo.testHelpers.TestUtilities;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserServiceInterface userService;

    @Autowired
    private ObjectMapper objectMapper;

    User user = TestUtilities.buildUser();

    @Test
    void createUser_Returns201() throws Exception {
        UserRequest userRequest = TestUtilities.buildUserRequest();

        when(userService.createUser(userRequest)).thenReturn(TestUtilities.buildUserResponse());

        MvcResult result = mockMvc.perform(post("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value(userRequest.getFirstName()))
                .andExpect(jsonPath("$.email").value(userRequest.getEmail())).andReturn();

        //deserialize response
        UserResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), UserResponse.class);
        assertThat(response.getFirstName()).isEqualTo(userRequest.getFirstName());
    }

    @Test
    void getUserById_UserExists_Returns200() throws Exception {
        UserResponse userResponse = TestUtilities.buildUserResponse();

        when(userService.getUserById(user.getId().toString())).thenReturn(Optional.of(userResponse));

        MvcResult result = mockMvc.perform(get("/api/v1/user/{id}", user.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.email").value(user.getEmail())).andReturn();

        //deserialize response
        UserResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), UserResponse.class);
        assertThat(response).isEqualTo(userResponse);
    }

    @Test
    void getUserById_UserNotFound_Returns404() throws Exception {
        when(userService.getUserById(user.getId().toString())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/user/{id}", user.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void updateUser_UserExists_Returns200() throws Exception {
        UserResponse userResponse = TestUtilities.buildUserResponseUpdated();
        UserUpdateRequest userUpdateRequest = TestUtilities.buildUserUpdateRequest();

        when(userService.updateUser(userUpdateRequest)).thenReturn(userResponse);

        MvcResult result = mockMvc.perform(put("/api/v1/user/{id}", userUpdateRequest.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userUpdateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value(userUpdateRequest.getAddress())).andReturn();

        //deserialize response
        UserResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), UserResponse.class);
        assertThat(response).isEqualTo(userResponse);
    }

    @Test
    void updateUser_UserNotFound_Returns404() throws Exception {
        UserUpdateRequest userUpdateRequest = TestUtilities.buildUserUpdateRequest();

        when(userService.updateUser(userUpdateRequest)).thenThrow(new UserNotFoundException("User not found."));

        mockMvc.perform(put("/api/v1/user/{id}", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userUpdateRequest)))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void deleteUser_Returns200() throws Exception {
        User user = TestUtilities.buildUser();

        doNothing().when(userService).deleteUser(String.valueOf(user.getId()));

        mockMvc.perform(delete("/api/v1/user/{id}", user.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteUser_UserNotFound_Returns404() throws Exception {
        User user = TestUtilities.buildUser();

        doThrow(new UserNotFoundException("User not found.")).when(userService).deleteUser(String.valueOf(user.getId()));

        mockMvc.perform(delete("/api/v1/user/{id}", user.getId()))
                .andExpect(status().isNotFound());
    }


}
