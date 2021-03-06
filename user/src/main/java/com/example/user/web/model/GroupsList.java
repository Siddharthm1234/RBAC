package com.example.user.web.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class GroupsList {
    private UserDto userDto;
    private Set<GroupDto> groupDtoSet;
}
