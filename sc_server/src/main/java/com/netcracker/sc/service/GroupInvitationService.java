package com.netcracker.sc.service;

import com.netcracker.sc.dto.GroupInvitationDTO;

import java.util.List;

public interface GroupInvitationService {
    Boolean addInvitation(Long groupId, String userLogin);
    List<GroupInvitationDTO> findByUserId(Long userId);
    Boolean acceptInvitation(GroupInvitationDTO groupInvitationDTO);
    Boolean declineInvitation(GroupInvitationDTO groupInvitationDTO);
}
