package com.netcracker.sc.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class GroupDTO {
    private Long groupId;
    private String name;
    private BigDecimal maxAmount;
    private BigDecimal currentAmount;
    private String description;
    private BigDecimal progress;
    private String imgName;
    private List<UserDTO> listOfUsers;
}
