package com.example.user.web.mapper;

import com.example.user.domain.User;
import com.example.user.web.model.UserDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface UserMapper {
    User userDtoToUser(UserDto userDto);
    UserDto userToUserDto(User user);
}
