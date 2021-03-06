package com.example.group.service;

import com.example.group.domain.Group;
import com.example.group.repository.GroupRepository;
import com.example.group.repository.GroupRoleRepository;
import com.example.group.web.exception.GroupNotFoundException;
import com.example.group.web.mapper.GroupMapper;
import com.example.group.web.mapper.GroupRoleMapper;
import com.example.group.web.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final GroupRoleRepository groupRoleRepository;
    private final GroupMapper groupMapper;
    private final GroupRoleMapper groupRoleMapper;
    private final RoleListByGroupIdHystrix roleListByGroupIdHystrix;


    @Override
    public Set<GroupDto> getGroups() {
        Set<GroupDto> groups = new HashSet<>();

        groupRepository.findAll().forEach(group -> {
            groups.add(groupMapper.groupToGroupDto(group));
        });
        return groups;

    }

    @Override
    public GroupDto getGroupById(Long groupId) {
        if(groupId==null){
            throw new GroupNotFoundException("Group cannot be null");
        }

        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if(groupOptional.isPresent()){
            return groupMapper.groupToGroupDto(groupOptional.get());
        }
        log.error("Invalid Group Id provided while using getGroupById: "+ groupId);
        throw new GroupNotFoundException("Invalid Group Id: "+ groupId);
    }

    @Override
    @Transactional
    public GroupDto updateGroupById(Long groupId, GroupDto groupDto) {
        if(groupId==null){
            throw new GroupNotFoundException("Group cannot be null");
        }


        Optional<Group> groupOptional = groupRepository.findById(groupId);

        if(groupOptional.isPresent()){
            Group group = groupOptional.get();

            group.setGroupName( groupDto.getGroupName() );
            group.setGroupDescription( groupDto.getGroupDescription() );

            return groupMapper.groupToGroupDto(groupRepository.save(group));
        }
        log.error("Invalid Group Id provided while using updateGroupById: "+ groupId);
        throw new GroupNotFoundException("Invalid Group Id :"+ groupId);
    }

    @Override
    @Transactional
    public GroupDto createGroup(GroupDto groupDto) {
        if(groupDto == null){
            throw new GroupNotFoundException("New Group cannot be Null");
        }

        return groupMapper.groupToGroupDto(groupRepository.save(groupMapper.groupDtoToGroup(groupDto)));
    }

    @Override
    @Transactional
    public void deleteById(Long groupId) {
        if(groupId==null){
            throw new GroupNotFoundException("Group cannot be null");
        }

        Optional<Group> groupOptional = groupRepository.findById(groupId);

        if(!groupOptional.isPresent()){
            log.error("Invalid Group Id provided while using deleteById: "+ groupId);
            throw new GroupNotFoundException("Invalid Group Id :"+ groupId);
        }

        groupRepository.deleteById(groupId);
    }

    /*----------------- Roles from Group Id -------------------*/
    @Override
    @Transactional
    public RolesList getRolesByGroupId(Long groupId) {
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if(!groupOptional.isPresent()){
            log.error("Invalid Group Id provided while using getRolesByGroupId: "+ groupId);
            throw new GroupNotFoundException("Invalid Group Id: "+ groupId);
        }
        Group group = groupOptional.get();

        Set<GroupRoleMappingDto> groupRoleMappingDtos = new HashSet<>();

        groupRoleRepository.findByGroupId(groupId).forEach(userGroupMappingDto -> {
            groupRoleMappingDtos.add(groupRoleMapper.groupRoleMappingToGroupRoleMappingDto(userGroupMappingDto));
        });

        ResponseEntity<RolesList>  rolesListResponseEntity = roleListByGroupIdHystrix.getRoleListByGroupId(groupRoleMappingDtos);
        RolesList rolesList = rolesListResponseEntity.getBody();
        rolesList.setGroupDto(groupMapper.groupToGroupDto(group));
        return rolesList;
    }

    /*----------------- Groups from User Name -------------------*/
    @Override
    public Set<GroupDto> getGroupsByUserId(Set<UserGroupMappingDto> userGroupMappingDtos) {
        Set<GroupDto> groupDtoSet = new HashSet<>();

        userGroupMappingDtos.forEach(userGroupMappingDto -> {
            groupDtoSet.add(this.getGroupById(userGroupMappingDto.getGroupId()));
        });

        return groupDtoSet;
    }
}
