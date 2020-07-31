package com.netcracker.sc.service;

import com.netcracker.sc.domain.Group;
import com.netcracker.sc.domain.User;
import com.netcracker.sc.domain.UserToGroup;
import com.netcracker.sc.dto.GroupCreationFormDTO;
import com.netcracker.sc.dto.GroupDTO;
import com.netcracker.sc.dto.RoleAssignmentDTO;
import com.netcracker.sc.dto.UserDTO;
import com.netcracker.sc.enums.GroupRole;
import com.netcracker.sc.mapper.GroupMapper;
import com.netcracker.sc.mapper.UserMapper;
import com.netcracker.sc.mapper.UserToGroupMapper;
import com.netcracker.sc.repository.GroupRepository;
import com.netcracker.sc.repository.UserRepository;
import com.netcracker.sc.repository.UserToGroupRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class GroupServiceImpl implements GroupService {
    private GroupRepository groupRepository;
    private GroupMapper groupMapper;
    private UserRepository userRepository;
    private UserToGroupRepository userToGroupRepository;
    private UserToGroupMapper userToGroupMapper;
    private UserMapper userMapper;

    public GroupServiceImpl(GroupRepository groupRepository, GroupMapper groupMapper, UserRepository userRepository, UserToGroupRepository userToGroupRepository, UserToGroupMapper userToGroupMapper, UserMapper userMapper) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
        this.userRepository = userRepository;
        this.userToGroupRepository = userToGroupRepository;
        this.userToGroupMapper = userToGroupMapper;
        this.userMapper = userMapper;
    }

    @Override
    public Group findById(Long groupId) {
        return groupRepository.findByGroupId(groupId);
    }

    @Transactional
    @Override
    public GroupDTO createGroup(GroupCreationFormDTO form) {
        try {
            Group group = groupRepository.save(groupMapper.toEntity(form));
            User user = userRepository.findByUserId(form.getCreatorId());
            UserToGroup userToGroup = new UserToGroup();
            userToGroup.setUser(user);
            userToGroup.setGroup(group);
            userToGroup.setGroupRole(GroupRole.OWNER);
            userToGroupRepository.save(userToGroup);
            return groupMapper.toDTO(group, this.getUsersByGroupId(group.getGroupId()));
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void assignRole(RoleAssignmentDTO roleAssignmentDTO) {
        UserToGroup userToGroup = userToGroupMapper.toEntity(roleAssignmentDTO);
        userToGroupRepository.updateRole(userToGroup.getGroupRole(), userToGroup.getUser(), userToGroup.getGroup());
    }

    @Transactional
    @Override
    public List<GroupDTO> getGroupsByUserId(Long userId) {
        User user = userRepository.findByUserId(userId);
        List<UserToGroup> userToGroups = user.getUserToGroupSet();
        List<Group> groups = new ArrayList<>();
        for (UserToGroup u:
             userToGroups) {
            groups.add(u.getGroup());
        }
        return groupMapper.toDTOs(groups);
    }

    @Transactional
    @Override
    public List<UserDTO> getUsersByGroupId(Long groupId) {

        Group group = groupRepository.findByGroupId(groupId);
        List<UserToGroup> userToGroups = userToGroupRepository.findByGroup(group);
        List<User> users = new ArrayList<>();
        //System.out.println(userToGroups.size());
        for (UserToGroup u:
                userToGroups) {
            users.add(u.getUser());
        }
        return userMapper.toEntities(users);
    }

    @Override
    public GroupRole getUserRoleInGroupByUserIdAndGroupId(Long userId, Long groupID) {
        User user = this.userRepository.findByUserId(userId);
        Group group = this.groupRepository.findByGroupId(groupID);
        UserToGroup userToGroup = this.userToGroupRepository.findByUserAndGroup(user,group);
        return userToGroup != null? userToGroup.getGroupRole(): null;
    }

    @Override
    public Boolean checkUserAccess(Long userId, Long groupId) {
        User user = userRepository.findByUserId(userId);
        Group group = groupRepository.findByGroupId(groupId);
        if(group == null) return null;
        UserToGroup userToGroup = this.userToGroupRepository.findByUserAndGroup(user,group);

        return userToGroup != null? true: null;
    }

    @Override
    public GroupDTO findGroupDtoById(Long groupId) {
        return this.groupMapper.toDTO(this.findById(groupId), this.getUsersByGroupId(groupId));
    }

    @Transactional
    @Override
    public List<GroupDTO> getGroupsForUserByRole(Long userId, GroupRole groupRole) {
        List<UserToGroup> userToGroups = userToGroupRepository.findByUserAndGroupRole(userRepository.findByUserId(userId), groupRole);
        List<Group> result = new ArrayList<>();
        for (UserToGroup u:
             userToGroups) {
            result.add(u.getGroup());
        }
        return groupMapper.toDTOs(result);
    }

    @Override
    public Boolean editGroup(Long groupId, GroupCreationFormDTO form) {
        try {
           // System.out.println(form.getName());
            Group result = groupRepository.findByGroupId(groupId);
            if (!StringUtils.isEmpty(form.getName())){
                result.setName(form.getName());
            }
            if (!StringUtils.isEmpty(form.getDescription())){
                result.setDescription(form.getDescription());
            }
            if (!StringUtils.isEmpty(form.getMaxAmount())){
                result.setMaxAmount(form.getMaxAmount());
            }
            groupRepository.save(result);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean deleteMember(Long groupId, Long userId) {
        try {
            userToGroupRepository.deleteMember(userRepository.findByUserId(userId), groupRepository.findByGroupId(groupId));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
