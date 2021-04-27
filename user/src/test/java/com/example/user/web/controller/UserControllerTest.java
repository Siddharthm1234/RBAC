package com.example.user.web.controller;

import com.example.user.service.UserService;
import com.example.user.web.dto.responseDto.AllUsersResponseDto;
import com.example.user.web.dto.responseDto.UserResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    MockMvc mockMvc;

    UserResponseDto validUser1;
    UserResponseDto validUser2;
    UserResponseDto validUser3;

    AllUsersResponseDto validAllUserResponseDto;

    @BeforeEach
    void setUp() {

        validUser1 = UserResponseDto.builder().userId(1L)
                .userName("smehta").firstName("Siddharth").lastName("Mehta")
                .createDate(OffsetDateTime.now()).lastModifiedDate(OffsetDateTime.now())
                .build();

        validUser2 = UserResponseDto.builder().userId(2L)
                .userName("gyadav").firstName("Gaurav").lastName("yadav")
                .createDate(OffsetDateTime.now()).lastModifiedDate(OffsetDateTime.now())
                .build();

        validUser3 = UserResponseDto.builder().userId(3L)
                .userName("agupta").firstName("Aman").lastName("Gupta")
                .createDate(OffsetDateTime.now()).lastModifiedDate(OffsetDateTime.now())
                .build();

        validAllUserResponseDto = new AllUsersResponseDto();
        Set<UserResponseDto> userResponseDtoSet = new HashSet<>();
        userResponseDtoSet.add(validUser1);
        userResponseDtoSet.add(validUser2);
        userResponseDtoSet.add(validUser3);
        validAllUserResponseDto.setUsers(userResponseDtoSet);


        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    @DisplayName("Get All Users")
    void getAllUsers() throws Exception {
        given(userService.getAllUsers()).willReturn(validAllUserResponseDto);

        mockMvc.perform(get("/users/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success.users.length()", is(3)));
    }

    @Test
    @DisplayName("Get A User By UserName")
    void getUserByName() throws Exception {
        given(userService.getUserByName(any())).willReturn(validUser1);

        mockMvc.perform(get("/users/" + validUser1.getUserName()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success.userId", is(1)))
                .andExpect(jsonPath("$.success.userName", is(validUser1.getUserName())))
                .andExpect(jsonPath("$.success.firstName", is(validUser1.getFirstName())))
                .andExpect(jsonPath("$.success.lastName", is(validUser1.getLastName())));
    }

    @Test
    @DisplayName("Update A User")
    void updateUserByName() throws Exception {
        given(userService.updateUserByName(any())).willReturn(validUser1);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(validUser1);

        mockMvc.perform(put("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success.userName", is(validUser1.getUserName())))
                .andExpect(jsonPath("$.success.firstName", is(validUser1.getFirstName())))
                .andExpect(jsonPath("$.success.lastName", is(validUser1.getLastName())));
    }

    @Test
    @DisplayName("Create A User")
    void createUser() throws Exception {
        given(userService.createUser(any())).willReturn(validUser1);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(validUser1);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success.userName", is(validUser1.getUserName())))
                .andExpect(jsonPath("$.success.firstName", is(validUser1.getFirstName())))
                .andExpect(jsonPath("$.success.lastName", is(validUser1.getLastName())));
    }

    @Test
    @DisplayName("Delete A User By UserName")
    void deleteByName() throws Exception {
        given(userService.deleteByName(any())).willReturn(validUser1);

        mockMvc.perform(delete("/users/" + validUser1.getUserName()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success.userId", is(1)))
                .andExpect(jsonPath("$.success.userName", is(validUser1.getUserName())))
                .andExpect(jsonPath("$.success.firstName", is(validUser1.getFirstName())))
                .andExpect(jsonPath("$.success.lastName", is(validUser1.getLastName())));
    }
}