package com.netcracker.sc.service;

import com.netcracker.sc.domain.GroupInvitation;
import com.netcracker.sc.domain.UserToGroup;
import com.netcracker.sc.dto.GroupInvitationDTO;
import com.netcracker.sc.enums.GroupRole;
import com.netcracker.sc.mapper.GroupInvitationMapper;
import com.netcracker.sc.repository.GroupInvitationRepository;
import com.netcracker.sc.repository.UserToGroupRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Service
public class GroupInvitationServiceImpl implements GroupInvitationService{

    private GroupInvitationRepository groupInvitationRepository;
    private GroupInvitationMapper groupInvitationMapper;
    private UserToGroupRepository userToGroupRepository;

    public GroupInvitationServiceImpl(GroupInvitationRepository groupInvitationRepository, GroupInvitationMapper groupInvitationMapper, UserToGroupRepository userToGroupRepository) {
        this.groupInvitationRepository = groupInvitationRepository;
        this.groupInvitationMapper = groupInvitationMapper;
        this.userToGroupRepository = userToGroupRepository;
    }

    @Transactional
    @Override
    public Boolean addInvitation(Long groupId, String userLogin) {
        try {
            GroupInvitation groupInvitation = groupInvitationMapper.toEntity(groupId,userLogin);
            if (userToGroupRepository.findByUserAndGroup(groupInvitation.getUser(), groupInvitation.getGroup()) != null) {
                log.info("already in the group");
                return false;
            }
            if (groupInvitationRepository.findByGroupAndUserAndIsDeleted( groupInvitation.getGroup(), groupInvitation.getUser(),false) == null) {
                groupInvitationRepository.save(groupInvitation);
                return true;
            }
            else {
                log.info("already invited");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<GroupInvitationDTO> findByUserId(Long userId) {
        return groupInvitationMapper.toDTOs(groupInvitationRepository.findByUserId(userId));
    }

    @Override
    public Boolean acceptInvitation(GroupInvitationDTO groupInvitationDTO) {
        GroupInvitation groupInvitation = groupInvitationMapper.toEntity(groupInvitationDTO);
        if (groupInvitation.getGroup() == null || groupInvitation.getUser() == null){
            return false;
        }
        UserToGroup userToGroup = new UserToGroup();
        userToGroup.setGroup(groupInvitation.getGroup());
        userToGroup.setUser(groupInvitation.getUser());
        userToGroup.setGroupRole(GroupRole.USER);
        try {
            userToGroupRepository.save(userToGroup);
            groupInvitationRepository.deleteInvitationById(groupInvitationDTO.getGroupInvitationId());
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean declineInvitation(GroupInvitationDTO groupInvitationDTO) {
        try {
            groupInvitationRepository.deleteInvitationById(groupInvitationDTO.getGroupInvitationId());
            return true;
        }catch (Exception e) {
            return false;
        }
    }
}
