package com.netcracker.sc.mapper;

import com.netcracker.sc.domain.UserToGroup;
import com.netcracker.sc.dto.RoleAssignmentDTO;
import com.netcracker.sc.repository.GroupRepository;
import com.netcracker.sc.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class UserToGroupMapper {

    private GroupRepository groupRepository;
    private UserRepository userRepository;

    public UserToGroupMapper(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public UserToGroup toEntity(RoleAssignmentDTO roleAssignmentDTO){
        UserToGroup result = new UserToGroup();
        result.setGroup(groupRepository.findByGroupId(roleAssignmentDTO.getGroupId()));
        if (result.getGroup() == null){
            log.error("There is no group with such id: " + roleAssignmentDTO.getGroupId());
            throw new IllegalArgumentException("There is no group with such id: " + roleAssignmentDTO.getGroupId());
        }
        result.setUser(userRepository.findByUserId(roleAssignmentDTO.getUserId()));
        if (result.getUser() == null){
            log.error("There is no user with such id: " + roleAssignmentDTO.getUserId());
            throw new IllegalArgumentException("There is no user with such id: " + roleAssignmentDTO.getUserId());
        }
        result.setGroupRole(roleAssignmentDTO.getNewRole());
        return result;
    }
}
