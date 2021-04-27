package com.example.group.service;

import com.example.group.domain.Group;
import com.example.group.domain.GroupRoleMapping;
import com.example.group.repository.GroupRepository;
import com.example.group.repository.GroupRoleRepository;
import com.example.group.web.exception.GroupNotFoundException;
import com.example.group.web.exception.GroupRoleNotFoundException;
import com.example.group.web.exception.GroupRoleNotUniqueException;
import com.example.group.web.mapper.GroupRoleMapper;
import com.example.group.web.dto.requestDto.GroupRoleMappingRequestDto;
import com.example.group.web.dto.requestDto.GroupRoleMappingUpdateRequestDto;
import com.example.group.web.dto.responseDto.AllGroupRoleMappingsResponseDto;
import com.example.group.web.dto.responseDto.GroupRoleMappingResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Implementation for GroupRole Service
 *
 * @author Siddharth Mehta
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GroupRoleServiceImpl implements GroupRoleService {

    private final GroupRoleRepository groupRoleRepository;
    private final GroupRepository groupRepository;
    private final GroupRoleMapper groupRoleMapper;
    private final ValidateRoleForGroupRoleMapping validateRoleForGroupRoleMapping;

    @Override
    public AllGroupRoleMappingsResponseDto getAllGroupRoleMappings() {
        AllGroupRoleMappingsResponseDto allGroupRoleMappingsResponseDto = new AllGroupRoleMappingsResponseDto();
        Set<GroupRoleMappingResponseDto> groupRoles = new HashSet<>();

        groupRoleRepository.findAll().forEach(groupRoleMapping -> {
            groupRoles.add(groupRoleMapper.groupRoleMappingToGroupRoleResponseMappingDto(groupRoleMapping));
        });

        allGroupRoleMappingsResponseDto.setGroupRoles(groupRoles);
        return allGroupRoleMappingsResponseDto;
    }

    @Override
    public GroupRoleMappingResponseDto getGroupRoleMappingById(Long groupRoleId) {
        if (groupRoleId == null) {
            log.error("[getGroupRoleMappingById] Group-Role Mapping cannot be Null");
            throw new GroupRoleNotFoundException("Group-Role Mapping cannot be Null");

        }

        Optional<GroupRoleMapping> groupRoleOptional = groupRoleRepository.findById(groupRoleId);
        if (groupRoleOptional.isPresent()) {
            return groupRoleMapper.groupRoleMappingToGroupRoleResponseMappingDto(groupRoleOptional.get());
        }
        log.error("[getGroupRoleMappingById] Invalid Group-Role Mapping with Id: " + groupRoleId);
        throw new GroupRoleNotFoundException("Invalid Group-Role Mapping with Id: " + groupRoleId);
    }

    @Override
    @Transactional
    public GroupRoleMappingResponseDto updateGroupRoleMappingById(GroupRoleMappingUpdateRequestDto groupRoleMappingUpdateRequestDto) {
        Long groupRoleId = groupRoleMappingUpdateRequestDto.getGroupRoleId();

        Optional<GroupRoleMapping> groupRoleOptional = groupRoleRepository.findById(groupRoleId);

        if (!groupRoleOptional.isPresent()) {
            log.error("[updateGroupRoleMappingById] Invalid Group-Role Mapping with Id :" + groupRoleId);
            throw new GroupRoleNotFoundException("Invalid Group-Role Mapping with Id :" + groupRoleId);
        } else {
            GroupRoleMapping groupRoleMapping = groupRoleOptional.get();

            Long groupId = groupRoleMappingUpdateRequestDto.getGroupId();
            Long roleId = groupRoleMappingUpdateRequestDto.getRoleId();

            /* Check if the same groupId and roleId combination already exists*/
            Optional<GroupRoleMapping> dtoGroupRoleMappingOptional = groupRoleRepository
                    .findByGroupIdAndAndRoleId(groupId, roleId);

            if (dtoGroupRoleMappingOptional.isPresent()) {
                GroupRoleMapping dtoGroupRoleMapping = dtoGroupRoleMappingOptional.get();
                if (groupRoleMapping.getGroupRoleId() != dtoGroupRoleMapping.getGroupRoleId()) {
                    log.error("[updateGroupRoleMappingById] GroupId: " + groupId + " and RoleId: " + roleId + " lookup value already exist");
                    throw new GroupRoleNotUniqueException("GroupId: " + groupId + " and RoleId: " + roleId + " lookup value already exist");
                }
            }

            /* Check for valid groupId */
            if (!validateGroupId(groupId)) {
                log.error("[updateGroupRoleMappingById] Invalid Group Id: " + groupId);
                throw new GroupNotFoundException("Invalid Group Id: " + groupId);
            }

            /*Check for valid roleId*/
            validateRoleForGroupRoleMapping.checkRoleExist(roleId);

            groupRoleMapping.setGroupId(groupRoleMappingUpdateRequestDto.getGroupId());
            groupRoleMapping.setRoleId(groupRoleMappingUpdateRequestDto.getRoleId());

            return groupRoleMapper.groupRoleMappingToGroupRoleResponseMappingDto(groupRoleRepository.save(groupRoleMapping));
        }

    }

    @Override
    @Transactional
    public GroupRoleMappingResponseDto createGroupRoleMapping(GroupRoleMappingRequestDto groupRoleMappingRequestDto) {

        Long groupId = groupRoleMappingRequestDto.getGroupId();
        Long roleId = groupRoleMappingRequestDto.getRoleId();

        /* Check for valid groupId */
        if (!validateGroupId(groupId)) {
            log.error("[createGroupRoleMapping] Invalid Group Id: " + groupId);
            throw new GroupNotFoundException("Invalid Group Id: " + groupId);
        }

        /*Check for valid roleId*/
        validateRoleForGroupRoleMapping.checkRoleExist(roleId);

        try {
            return groupRoleMapper.groupRoleMappingToGroupRoleResponseMappingDto(groupRoleRepository
                    .save(groupRoleMapper.groupRoleMappingRequestDtoToGroupRoleMapping(groupRoleMappingRequestDto)));
        } catch (DataIntegrityViolationException ex) {
            log.error("[createGroupRoleMapping] GroupId: " + groupId + " and RoleId: " + roleId + " lookup value already exist");
            throw new GroupRoleNotUniqueException("GroupId: " + groupId + " and RoleId: " + roleId + " lookup value already exist");
        }
    }


    @Override
    @Transactional
    public GroupRoleMappingResponseDto deleteByGroupIdAndRoleId(Long groupId, Long roleId) {
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if (!groupOptional.isPresent()) {
            log.error("[deleteByGroupIdAndRoleId] Invalid Group Id: " + groupId);
            throw new GroupNotFoundException("Invalid Group Id: " + groupId);
        }

        Optional<GroupRoleMapping> groupMappingByUserIdAndGroupId = groupRoleRepository.findByGroupIdAndAndRoleId(groupId, roleId);

        if (!groupMappingByUserIdAndGroupId.isPresent()) {
            log.error("[deleteByGroupIdAndRoleId] Invalid Group-Role Mapping with Group Id: " + groupId + " and Role Id: " + roleId);
            throw new GroupRoleNotFoundException("Invalid Group-Role Mapping with Group Id: " + groupId + " and Role Id: " + roleId);
        }
        groupRoleRepository.deleteByGroupIdAndRoleId(groupId, roleId);
        return groupRoleMapper.groupRoleMappingToGroupRoleResponseMappingDto(groupMappingByUserIdAndGroupId.get());
    }

    public boolean validateGroupId(Long groupId) {
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        return (groupOptional.isPresent());
    }
}
