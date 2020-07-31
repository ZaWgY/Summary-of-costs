package com.netcracker.sc.dto;

import lombok.Data;

@Data
public class GroupInvitationDTO {
    private Long groupInvitationId;
    private GroupListDto groupListDto;
    private Long userId;
}
