package com.netcracker.sc.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SpendingDTO {
    private BigDecimal amount;
    private Long groupId;
    private Long categoryId;
    private Long userId;
}
