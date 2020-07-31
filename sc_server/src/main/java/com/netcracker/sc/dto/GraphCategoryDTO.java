package com.netcracker.sc.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GraphCategoryDTO {
    private BigDecimal amount;
    private String categoryName;
}
