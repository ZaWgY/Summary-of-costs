package com.netcracker.sc.dto;

import com.netcracker.sc.enums.GroupRole;
import lombok.Data;

@Data
public class RoleAssignmentDTO {
    private Long userId;
    private Long groupId;
    private GroupRole newRole;
}
