package com.netcracker.sc.service;

import com.netcracker.sc.domain.Group;
import com.netcracker.sc.dto.GroupCreationFormDTO;
import com.netcracker.sc.dto.GroupDTO;
import com.netcracker.sc.dto.RoleAssignmentDTO;
import com.netcracker.sc.dto.UserDTO;
import com.netcracker.sc.enums.GroupRole;

import java.util.List;

public interface GroupService {
    Group findById(Long groupId);
    GroupDTO createGroup(GroupCreationFormDTO form);
    Boolean editGroup(Long groupId, GroupCreationFormDTO form);
    void assignRole(RoleAssignmentDTO roleAssignmentDTO);
    List<GroupDTO> getGroupsByUserId(Long userId);
    GroupDTO findGroupDtoById(Long groupId);
    List<UserDTO> getUsersByGroupId(Long groupId);
    List<GroupDTO> getGroupsForUserByRole(Long userId, GroupRole groupRole);
    GroupRole getUserRoleInGroupByUserIdAndGroupId(Long userId, Long groupID);
    Boolean deleteMember(Long groupId, Long userId);

    Boolean checkUserAccess(Long userId, Long groupId);
}
