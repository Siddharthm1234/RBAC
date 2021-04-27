package com.example.user.service;

import com.example.user.domain.User;
import com.example.user.repository.UserGroupRepository;
import com.example.user.repository.UserRepository;
import com.example.user.web.dto.requestDto.UserRequestDto;
import com.example.user.web.dto.responseDto.AllUsersResponseDto;
import com.example.user.web.dto.responseDto.UserResponseDto;
import com.example.user.web.exception.UserNotFoundException;
import com.example.user.web.mapper.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserMapper userMapperMock;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private UserGroupRepository userGroupRepositoryMock;

    @InjectMocks
    UserServiceImpl userService;

    UserRequestDto userRequestDtoMock1;
    UserRequestDto userRequestDtoMock2;
    User userMock1;
    User userMock2;
    UserResponseDto userResponseDtoMock1;
    UserResponseDto userResponseDtoMock2;

    @BeforeEach
    public void setUp() {
        //Some Mock UserRequest Objects
        userRequestDtoMock1 = UserRequestDto.builder()
                .userName("smehta").firstName("Sid").lastName("Mehta")
                .build();
        userRequestDtoMock2 = UserRequestDto.builder()
                .userName("gyadav").firstName("Gaurav").lastName("Yadav")
                .build();


        //Some Mock User Objects
        userMock1 = User.builder().userId(1L)
                .userName("smehta").firstName("Sid").lastName("Mehta")
                .build();
        userMock2 = User.builder().userId(2L)
                .userName("gyadav").firstName("Gaurav").lastName("Yadav")
                .build();


        //Couple of Mock UserResponseDto objects
        userResponseDtoMock1 = UserResponseDto.builder().userId(1L)
                .userName("smehta").firstName("Sid").lastName("Mehta")
                .build();
        userResponseDtoMock2 = UserResponseDto.builder().userId(2L)
                .userName("gyadav").firstName("Gaurav").lastName("Yadav")
                .build();
    }

    @Test
    @DisplayName("Get all Users")
    void getAllUsers() {
        when(userRepositoryMock.findAll()).thenReturn(Arrays.asList(userMock1, userMock2));
        when(userMapperMock.userToUserResponseDto(userMock1)).thenReturn(userResponseDtoMock1);
        when(userMapperMock.userToUserResponseDto(userMock2)).thenReturn(userResponseDtoMock2);

        AllUsersResponseDto allUsersResponseDtoActual = userService.getAllUsers();
        Set<UserResponseDto> userResponseDtoSet = allUsersResponseDtoActual.getUsers();

        assertEquals(2, userResponseDtoSet.size());
        verify(userRepositoryMock, times(1)).findAll();
    }

    @Test
    @DisplayName("Get a User that Exists")
    void getUserByNameValid() {
        when(userRepositoryMock.findByUserName("smehta")).thenReturn(Optional.of(userMock1));
        when(userMapperMock.userToUserResponseDto(userMock1)).thenReturn(userResponseDtoMock1);

        UserResponseDto userResponseDtoActual = userService.getUserByName("smehta");
        assertEquals(userResponseDtoMock1.getUserName(), userResponseDtoActual.getUserName());
        assertEquals(userResponseDtoMock1.getFirstName(), userResponseDtoActual.getFirstName());
        assertEquals(userResponseDtoMock1.getLastName(), userResponseDtoActual.getLastName());
        verify(userRepositoryMock, times(1)).findByUserName(anyString());
    }

    @Test
    @DisplayName("Get a User that doesn't Exists")
    void getUserByNameInvalid() {
        when(userRepositoryMock.findByUserName("smehtaa")).thenThrow(UserNotFoundException.class);

        Assertions.assertThrows(UserNotFoundException.class, () -> {
            userService.getUserByName("smehtaa");
        });
        verify(userRepositoryMock, times(1)).findByUserName(anyString());
    }

    @Test
    @DisplayName("Update a Valid User")
    void updateUserByNameValid() {
        when(userRepositoryMock.findByUserName(anyString())).thenReturn(Optional.of(userMock2));
        when(userRepositoryMock.save(userMock2)).thenReturn(userMock2);
        when(userMapperMock.userToUserResponseDto(userMock2)).thenReturn(userResponseDtoMock2);

        UserResponseDto userResponseDtoActual = userService.updateUserByName(userRequestDtoMock2);

        assertEquals(userResponseDtoMock2.getUserName(), userResponseDtoActual.getUserName());
        assertEquals(userResponseDtoMock2.getFirstName(), userResponseDtoActual.getFirstName());
        assertEquals(userResponseDtoMock2.getLastName(), userResponseDtoActual.getLastName());
        verify(userRepositoryMock, times(1)).findByUserName(anyString());

    }

    @Test
    @DisplayName("Create a Valid User")
    void createUserValid() {
        when(userMapperMock.userRequestDtoToUser(userRequestDtoMock2)).thenReturn(userMock2);
        when(userRepositoryMock.save(userMock2)).thenReturn(userMock2);
        when(userMapperMock.userToUserResponseDto(userMock2)).thenReturn(userResponseDtoMock2);

        UserResponseDto userResponseDtoActual = userService.createUser(userRequestDtoMock2);

        assertEquals(userResponseDtoActual, userResponseDtoMock2);
        verify(userRepositoryMock, times(1)).save(any());
    }

    @Test
    @DisplayName("Delete a Valid User")
    void deleteByNameValid() {
        when(userRepositoryMock.findByUserName("smehta")).thenReturn(Optional.of(userMock1));
        when(userMapperMock.userToUserResponseDto(userMock1)).thenReturn(userResponseDtoMock1);

        UserResponseDto userResponseDtoActual = userService.deleteByName("smehta");

        assertEquals(userResponseDtoActual, userResponseDtoMock1);
        verify(userRepositoryMock, times(1)).deleteByUserName(any());
        verify(userGroupRepositoryMock, times(1)).deleteByUserId(any());
    }

    @Test
    @DisplayName("Delete an Invalid User with userName field Null or Empty")
    void deleteByNameWhereNameNull() {
        Assertions.assertThrows(UserNotFoundException.class, () -> {
            userService.deleteByName(null);
        });
    }


}