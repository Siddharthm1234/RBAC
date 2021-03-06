package com.example.group.bootstrap;

import com.example.group.domain.Group;
import com.example.group.repository.GroupRepository;
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

    private final GroupRepository groupRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        loadGroupObjects();
    }

    private void loadGroupObjects() {
        if (groupRepository.count() == 0) {
            log.info("Loading Group Bootstrap Data");

            groupRepository.save(
                    Group.builder()
                            .groupName("Group Java")
                            .groupDescription("Group Working on Java - Spring Applications")
                            .build()
            );

            groupRepository.save(
                    Group.builder()
                            .groupName("Group React")
                            .groupDescription("Group Working on JavaScript - React")
                            .build()
            );

            groupRepository.save(
                    Group.builder()
                            .groupName("Group Testers")
                            .groupDescription("Group Working on Test Driven Development")
                            .build()
            );
        }

    }
}
