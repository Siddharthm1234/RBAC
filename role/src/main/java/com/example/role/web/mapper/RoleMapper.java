package com.example.role.web.mapper;

import com.example.role.domain.Role;
import com.example.role.web.dto.requestDto.RoleRequestDto;
import com.example.role.web.dto.responseDto.RoleResponseDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface RoleMapper {
    Role roleRequestDtoToRole(RoleRequestDto roleRequestDto);

    RoleResponseDto roleToRoleResponseDto(Role role);
}
