package com.netcracker.sc.mapper;

import com.netcracker.sc.domain.GroupInvitation;
import com.netcracker.sc.dto.GroupInvitationDTO;
import com.netcracker.sc.repository.GroupInvitationRepository;
import com.netcracker.sc.repository.GroupRepository;
import com.netcracker.sc.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class GroupInvitationMapper {

    private GroupRepository groupRepository;
    private UserRepository userRepository;
    private GroupInvitationRepository groupInvitationRepository;
    private GroupMapper groupMapper;

    public GroupInvitationMapper(GroupRepository groupRepository, UserRepository userRepository, GroupInvitationRepository groupInvitationRepository, GroupMapper groupMapper) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.groupInvitationRepository = groupInvitationRepository;
        this.groupMapper = groupMapper;
    }

    public GroupInvitation toEntity(GroupInvitationDTO groupInvitationDTO){
        GroupInvitation result = groupInvitationRepository.findByGroupInvitationId(groupInvitationDTO.getGroupInvitationId());
        if (result == null){
            log.error("There is no invitation with such id: " + groupInvitationDTO.getGroupInvitationId());
            throw new IllegalArgumentException("There is no invitation with such id: " + groupInvitationDTO.getGroupInvitationId());
        }
        result.setGroup(groupRepository.findByGroupId(groupInvitationDTO.getGroupListDto().getGroupId()));
        result.setUser(userRepository.findByUserId(groupInvitationDTO.getUserId()));
        return result;
    }

    public GroupInvitation toEntity(Long groupId, String userLogin){
        GroupInvitation result = new GroupInvitation();
        result.setGroup(groupRepository.findByGroupId(groupId));
        if (result.getGroup() == null){
            log.error("There is no group with such id: " + groupId);
            throw new IllegalArgumentException("There is no group with such id: " + groupId);
        }
        result.setUser(userRepository.findByLogin(userLogin));
        if (result.getUser() == null){
            log.error("There is no user with such id: " + userLogin);
            throw new IllegalArgumentException("There is no user with such login: " + userLogin);
        }
        result.setIsDeleted(false);
        return result;
    }

    public GroupInvitationDTO toDTO(GroupInvitation groupInvitation){
        GroupInvitationDTO result = new GroupInvitationDTO();
        result.setGroupListDto(groupMapper.toGroupListDTO(groupInvitation.getGroup()));
        result.setGroupInvitationId(groupInvitation.getGroupInvitationId());
        result.setUserId(groupInvitation.getUser().getUserId());
        return result;
    }

    public List<GroupInvitationDTO> toDTOs(List<GroupInvitation> groupInvitations){
        List<GroupInvitationDTO> result = new ArrayList<>();
        for (GroupInvitation groupInvitation:
                groupInvitations) {
            result.add(toDTO(groupInvitation));
        }
        return result;
    }
}
