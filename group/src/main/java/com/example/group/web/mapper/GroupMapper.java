package com.example.group.web.mapper;

import com.example.group.domain.Group;
import com.example.group.web.model.GroupDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface GroupMapper {
    Group groupDtoToGroup(GroupDto groupDto);
    GroupDto groupToGroupDto(Group group);

}
