package com.example.role.service;

import com.example.role.domain.Role;
import com.example.role.repository.RoleRepository;
import com.example.role.web.exception.RoleNotFoundException;
import com.example.role.web.mapper.RoleMapper;
import com.example.role.web.model.RoleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleDto getRoleById(Long roleId) {
        if(roleId==null){
            throw new RoleNotFoundException("Role cannot be null");
        }
        Optional<Role> roleOpt = roleRepository.findById(roleId);
        if (roleOpt.isPresent()){
            return roleMapper.roleToRoleDto(roleOpt.get());
        }
        throw new RoleNotFoundException("Invalid Role Id :"+ roleId);
    }

    @Override
    public Set<RoleDto> getRoles(){
        Set<RoleDto> roles = new HashSet<>();

        roleRepository.findAll().forEach(role -> {
            roles.add(roleMapper.roleToRoleDto(role));
        });

        return roles;
    }

    @Override
    @Transactional
    public RoleDto updateRoleById(Long roleId, RoleDto roleDto) {
        if(roleId==null){
            throw new RoleNotFoundException("Role cannot be null");
        }


        Optional<Role> roleOpt = roleRepository.findById(roleId);

        if(roleOpt.isPresent()){
            Role role = roleOpt.get();

            role.setRoleName( roleDto.getRoleName() );
            role.setRoleDescription( roleDto.getRoleDescription() );

            return roleMapper.roleToRoleDto(roleRepository.save(role));
        }

        throw new RoleNotFoundException("Invalid Role Id :"+ roleId);
    }

    @Override
    @Transactional
    public RoleDto createRole(RoleDto roleDto) {
        if(roleDto == null){
            throw new RoleNotFoundException("New Role cannot be Null");
        }

        return roleMapper.roleToRoleDto(roleRepository.save(roleMapper.roleDtoToRole(roleDto)));
    }


    @Override
    @Transactional
    public void deleteById(Long roleId) {
        if(roleId==null){
            throw new RoleNotFoundException("Role cannot be null");
        }

        Optional<Role> roleOpt = roleRepository.findById(roleId);

        if(!roleOpt.isPresent()){
            throw new RoleNotFoundException("Invalid Role Id :"+ roleId);
        }

        roleRepository.deleteById(roleId);
    }

}
