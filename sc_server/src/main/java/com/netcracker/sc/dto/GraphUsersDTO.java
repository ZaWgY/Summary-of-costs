package com.netcracker.sc.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GraphUsersDTO {
    private BigDecimal amount;
    private String login;
}
