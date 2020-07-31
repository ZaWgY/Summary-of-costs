package com.netcracker.sc.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Data
public class GroupCreationFormDTO {
    private String name;
    private String description;
    private Long creatorId;
    private BigDecimal maxAmount;
}
