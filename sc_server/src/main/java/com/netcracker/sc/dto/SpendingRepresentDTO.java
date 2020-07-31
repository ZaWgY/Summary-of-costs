package com.netcracker.sc.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SpendingRepresentDTO {
    private Long id;
    private BigDecimal amount;
    private String categoryName;
    private String login;
    private String date;
}
