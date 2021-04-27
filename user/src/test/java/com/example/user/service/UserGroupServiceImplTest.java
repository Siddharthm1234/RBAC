package com.example.user.service;

import com.example.user.domain.User;
import com.example.user.domain.UserGroupMapping;
import com.example.user.repository.UserGroupRepository;
import com.example.user.repository.UserRepository;
import com.example.user.web.dto.requestDto.UserGroupMappingRequestDto;
import com.example.user.web.dto.requestDto.UserGroupMappingUpdateRequestDto;
import com.example.user.web.dto.responseDto.AllUserGroupMappingsResponseDto;
import com.example.user.web.dto.responseDto.UserGroupMappingResponseDto;
import com.example.user.web.exception.UserGroupNotFoundException;
import com.example.user.web.exception.UserNotFoundException;
import com.example.user.web.mapper.UserGroupMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserGroupServiceImplTest {

    @Mock
    private UserGroupMapper userGroupMapperMock;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private UserGroupRepository userGroupRepositoryMock;

    @Mock
    private ValidateGroupForUserGroupMapping validateGroupForUserGroupMapping;

    @InjectMocks
    UserGroupServiceImpl userGroupService;

    UserGroupMappingRequestDto userGroupMappingRequestDtoMock1;
    UserGroupMappingUpdateRequestDto userGroupMappingUpdateRequestDtoMock1;
    UserGroupMappingUpdateRequestDto userGroupMappingUpdateRequestDtoMock2;
    UserGroupMapping userGroupMappingMock1;
    UserGroupMapping userGroupMappingMock2;
    UserGroupMappingResponseDto userGroupMappingResponseDtoMock1;
    UserGroupMappingResponseDto userGroupMappingResponseDtoMock2;

    User userMock2;

    @BeforeEach
    public void setUp() {
        //Some Mock UserGroupMappingRequestDto
        userGroupMappingRequestDtoMock1 = UserGroupMappingRequestDto.builder()
                .userGroupId(1L).userId(1L).groupId(1L).build();
        //Some Mock UserGroupMappingUpdateRequest Objects
        userGroupMappingUpdateRequestDtoMock1 = UserGroupMappingUpdateRequestDto.builder()
                .userGroupId(1L).userId(1L).groupId(1L).build();
        userGroupMappingUpdateRequestDtoMock2 = UserGroupMappingUpdateRequestDto.builder()
                .userGroupId(2L).userId(2L).groupId(2L).build();


        //Some Mock UserGroupMapping Objects
        userGroupMappingMock1 = UserGroupMapping.builder().userId(1L)
                .userGroupId(1L).userId(1L).groupId(1L).build();
        userGroupMappingMock2 = UserGroupMapping.builder().userId(1L)
                .userGroupId(2L).userId(2L).groupId(2L).build();


        //Couple of Mock UserGroupMappingResponseDto objects
        userGroupMappingResponseDtoMock1 = UserGroupMappingResponseDto.builder()
                .userGroupId(1L).userId(1L).groupId(1L).build();
        userGroupMappingResponseDtoMock2 = UserGroupMappingResponseDto.builder()
                .userGroupId(2L).userId(2L).groupId(2L).build();

        //Some Mock User Objects
        userMock2 = User.builder().userId(2L)
                .userName("gyadav").firstName("Gaurav").lastName("Yadav")
                .build();
    }

    @Test
    @DisplayName("Get all User Group Mappings")
    void getAllUserGroupMappings() {
        when(userGroupRepositoryMock.findAll()).thenReturn(Arrays.asList(userGroupMappingMock1, userGroupMappingMock2));
        when(userGroupMapperMock.userGroupMappingToUserGroupMappingResponseDto(userGroupMappingMock1)).thenReturn(userGroupMappingResponseDtoMock1);
        when(userGroupMapperMock.userGroupMappingToUserGroupMappingResponseDto(userGroupMappingMock2)).thenReturn(userGroupMappingResponseDtoMock2);

        AllUserGroupMappingsResponseDto allUserGroupMappingsResponseDtoActual = userGroupService.getAllUserGroupMappings();
        Set<UserGroupMappingResponseDto> userGroupMappingResponseDtos = allUserGroupMappingsResponseDtoActual.getUserGroups();

        assertEquals(2, userGroupMappingResponseDtos.size());
        verify(userGroupRepositoryMock, times(1)).findAll();
    }

    @Test
    @DisplayName("Get a User Group Mapping that Exists")
    void getUserGroupMappingByIdValid() {
        when(userGroupRepositoryMock.findById(1L)).thenReturn(Optional.of(userGroupMappingMock1));
        when(userGroupMapperMock.userGroupMappingToUserGroupMappingResponseDto(userGroupMappingMock1)).thenReturn(userGroupMappingResponseDtoMock1);

        UserGroupMappingResponseDto userGroupMappingByIdActual = userGroupService.getUserGroupMappingById(String.valueOf(1L));
        assertEquals(userGroupMappingByIdActual.getUserGroupId(), userGroupMappingResponseDtoMock1.getUserGroupId());
        assertEquals(userGroupMappingByIdActual.getUserId(), userGroupMappingResponseDtoMock1.getUserId());
        assertEquals(userGroupMappingByIdActual.getGroupId(), userGroupMappingResponseDtoMock1.getGroupId());
        verify(userGroupRepositoryMock, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("Get a User Group Mapping that doesn't Exists")
    void getUserGroupMappingByIdInValid() {
        when(userGroupRepositoryMock.findById(3L)).thenThrow(UserGroupNotFoundException.class);

        assertThrows(UserGroupNotFoundException.class, () -> {
            userGroupService.getUserGroupMappingById(String.valueOf(3L));
        });
        verify(userGroupRepositoryMock, times(1)).findById(3L);
    }

    @Test
    @DisplayName("Update a Valid User Group Mapping")
    void updateUserGroupMappingByIdValid() {
        when(userGroupRepositoryMock.findById(2L)).thenReturn(Optional.of(userGroupMappingMock2));
        when(userGroupRepositoryMock.save(userGroupMappingMock2)).thenReturn(userGroupMappingMock2);
        when(userRepositoryMock.findById(2L)).thenReturn(Optional.of(userMock2));

        when(userGroupMapperMock.userGroupMappingToUserGroupMappingResponseDto(userGroupMappingMock2)).thenReturn(userGroupMappingResponseDtoMock2);

        validateGroupForUserGroupMapping.checkGroupExist(2L);

        UserGroupMappingResponseDto userGroupMappingResponseDtoActual = userGroupService.updateUserGroupMappingById(userGroupMappingUpdateRequestDtoMock2);

        assertEquals(userGroupMappingUpdateRequestDtoMock2.getUserGroupId(), userGroupMappingResponseDtoActual.getUserGroupId());
        assertEquals(userGroupMappingUpdateRequestDtoMock2.getUserId(), userGroupMappingResponseDtoActual.getUserId());
        assertEquals(userGroupMappingUpdateRequestDtoMock2.getGroupId(), userGroupMappingResponseDtoActual.getGroupId());
        verify(userGroupRepositoryMock, times(1)).findById(2L);

    }

    @Test
    @DisplayName("Create a Valid User")
    void createUserValid() {
        when(userGroupMapperMock.userGroupMappingRequestDtoToUserGroup(userGroupMappingRequestDtoMock1)).thenReturn(userGroupMappingMock1);
        when(userGroupRepositoryMock.save(userGroupMappingMock1)).thenReturn(userGroupMappingMock1);
        when(userGroupMapperMock.userGroupMappingToUserGroupMappingResponseDto(userGroupMappingMock1)).thenReturn(userGroupMappingResponseDtoMock1);

        when(userRepositoryMock.findById(1L)).thenReturn(Optional.of(userMock2));
        validateGroupForUserGroupMapping.checkGroupExist(2L);

        UserGroupMappingResponseDto userGroupMappingActual = userGroupService.createUserGroupMapping(userGroupMappingRequestDtoMock1);

        assertEquals(userGroupMappingActual, userGroupMappingResponseDtoMock1);
        verify(userGroupRepositoryMock, times(1)).save(any());
    }

    @Test
    @DisplayName("Delete a Valid User Group Mapping")
    void deleteByNameValid() {
        when(userRepositoryMock.findByUserName("gyadav")).thenReturn(Optional.of(userMock2));
        when(userGroupRepositoryMock.findUserGroupMappingByUserIdAndGroupId(2L, 2L)).thenReturn(Optional.of(userGroupMappingMock2));
        when(userGroupMapperMock.userGroupMappingToUserGroupMappingResponseDto(userGroupMappingMock2)).thenReturn(userGroupMappingResponseDtoMock2);

        userGroupRepositoryMock.deleteByUserIdAndGroupId(2L, 2L);

        UserGroupMappingResponseDto userGroupMappingResponseDtoActual = userGroupService.deleteByUserIdAndGroupId("gyadav", 2L);

        assertEquals(userGroupMappingResponseDtoActual, userGroupMappingResponseDtoMock2);
        verify(userRepositoryMock, times(1)).findByUserName(any());
        verify(userGroupRepositoryMock, times(1)).findUserGroupMappingByUserIdAndGroupId(anyLong(), anyLong());
    }

    @Test
    @DisplayName("Delete an Invalid User Group Mapping where userName field Null")
    void deleteByNameWhereNameNull() {
        Assertions.assertThrows(UserNotFoundException.class, () -> {
            userGroupService.deleteByUserIdAndGroupId(null, 1L);
        });
    }

    @Test
    @DisplayName("Delete an Invalid User Group Mapping where userName and groupId look up doesn't Exist")
    void deleteByNameWhereNameAndGroupIdDoesntExist() {
        when(userRepositoryMock.findByUserName("gyadav")).thenReturn(Optional.of(userMock2));
        Assertions.assertThrows(UserGroupNotFoundException.class, () -> {
            userGroupService.deleteByUserIdAndGroupId("gyadav", 1L);
        });
    }
}