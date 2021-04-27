package com.example.user.web.controller;

import com.example.user.service.UserGroupService;
import com.example.user.web.dto.requestDto.UserGroupMappingUpdateRequestDto;
import com.example.user.web.dto.responseDto.AllUserGroupMappingsResponseDto;
import com.example.user.web.dto.responseDto.UserGroupMappingResponseDto;
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

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserGroupControllerTest {
    @Mock
    UserGroupService userGroupService;

    @InjectMocks
    UserGroupController userGroupController;

    MockMvc mockMvc;

    UserGroupMappingResponseDto validUserGroupMapping1;
    UserGroupMappingResponseDto validUserGroupMapping2;
    UserGroupMappingResponseDto validUserGroupMapping3;

    UserGroupMappingUpdateRequestDto validUserGroupMappingUpdateRequestDto1;

    AllUserGroupMappingsResponseDto validAllUserGroupMappingsResponseDto;

    UserResponseDto validUser1;

    @BeforeEach
    void setUp() {

        validUserGroupMapping1 = UserGroupMappingResponseDto.builder()
                .userGroupId(1L).userId(1L).groupId(1L).build();

        validUserGroupMapping2 = UserGroupMappingResponseDto.builder()
                .userGroupId(2L).userId(2L).groupId(2L).build();

        validUserGroupMapping3 = UserGroupMappingResponseDto.builder()
                .userGroupId(3L).userId(3L).groupId(3L).build();

        validUserGroupMappingUpdateRequestDto1 = UserGroupMappingUpdateRequestDto.builder()
                .userId(1L).groupId(1L).build();


        validAllUserGroupMappingsResponseDto = new AllUserGroupMappingsResponseDto();
        Set<UserGroupMappingResponseDto> userGroupMappingResponseDtos = new HashSet<>();
        userGroupMappingResponseDtos.add(validUserGroupMapping1);
        userGroupMappingResponseDtos.add(validUserGroupMapping2);
        userGroupMappingResponseDtos.add(validUserGroupMapping3);
        validAllUserGroupMappingsResponseDto.setUserGroups(userGroupMappingResponseDtos);

        validUser1 = UserResponseDto.builder().userId(1L)
                .userName("smehta").firstName("Siddharth").lastName("Mehta")
                .createDate(OffsetDateTime.now()).lastModifiedDate(OffsetDateTime.now())
                .build();


        mockMvc = MockMvcBuilders.standaloneSetup(userGroupController).build();
    }

    @Test
    @DisplayName("Get All User Groups")
    void getAllUserGroups() throws Exception {
        given(userGroupService.getAllUserGroupMappings()).willReturn(validAllUserGroupMappingsResponseDto);

        mockMvc.perform(get("/user-groups/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success.userGroups.length()", is(3)));
    }

    @Test
    @DisplayName("Get A User-Group Mapping By userGroupId")
    void getUserGroupMappingById() throws Exception {
        given(userGroupService.getUserGroupMappingById(any())).willReturn(validUserGroupMapping1);

        mockMvc.perform(get("/user-groups/" + validUserGroupMapping1.getUserGroupId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success.userGroupId", is(1)))
                .andExpect(jsonPath("$.success.userId", is(1)))
                .andExpect(jsonPath("$.success.groupId", is(1)));
    }

    @Test
    @DisplayName("Update A User-Group Mapping")
    void updateUserGroupMappingById() throws Exception {
        given(userGroupService.updateUserGroupMappingById(any())).willReturn(validUserGroupMapping1);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(validUserGroupMapping1);

        mockMvc.perform(put("/user-groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success.userGroupId", is(1)))
                .andExpect(jsonPath("$.success.userId", is(1)))
                .andExpect(jsonPath("$.success.groupId", is(1)));
    }

    @Test
    @DisplayName("Create A User-Group Mapping")
    void createUserGroupMapping() throws Exception {
        given(userGroupService.createUserGroupMapping(any())).willReturn(validUserGroupMapping1);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(validUserGroupMappingUpdateRequestDto1);

        mockMvc.perform(post("/user-groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success.userGroupId", is(1)))
                .andExpect(jsonPath("$.success.userId", is(1)))
                .andExpect(jsonPath("$.success.groupId", is(1)));
    }

    @Test
    @DisplayName("Delete A User-Group Mapping Using UserName and GroupId")
    void deleteById() throws Exception {
        given(userGroupService.deleteByUserIdAndGroupId(any(), any())).willReturn(validUserGroupMapping1);

        mockMvc.perform(delete("/user-groups/user/" + validUser1.getUserName() + "/group/" + validUserGroupMapping1.getGroupId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success.userGroupId", is(1)))
                .andExpect(jsonPath("$.success.userId", is(1)))
                .andExpect(jsonPath("$.success.groupId", is(1)));
    }
}