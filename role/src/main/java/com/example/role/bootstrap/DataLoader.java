package com.example.role.bootstrap;

import com.example.role.domain.Role;
import com.example.role.repository.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Slf4j
@Component
@AllArgsConstructor
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        loadRoleObjects();
    }

    private void loadRoleObjects() {
        if(roleRepository.count()==0){


            roleRepository.save(
                    Role.builder()
                            .roleName("User")
                            .roleDescription("Permission to view only.")
                        .build()
            );


            roleRepository.save(
                    Role.builder()
                            .roleName("Admin")
                            .roleDescription("Permission to CRUD.")

                            .build()
            );
        }
        log.debug("Loading Role Bootstrap Data");
    }
}
