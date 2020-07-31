package com.netcracker.sc.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class GraphLineCategoriesUserDTO {
    private String categoryName;
    private List<BigDecimal> spendings;
}
