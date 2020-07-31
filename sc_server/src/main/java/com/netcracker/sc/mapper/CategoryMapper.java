package com.netcracker.sc.mapper;

import com.netcracker.sc.domain.Category;
import com.netcracker.sc.dto.CategoryDTO;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {

    public static CategoryDTO toDTO(Category category){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName(category.getName());
        categoryDTO.setCategoryId(category.getCategoryId());
        return categoryDTO;
    }

    public static List<CategoryDTO> toListDTO(List<Category> categories) {
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for (Category c:
             categories) {
            categoryDTOS.add(toDTO(c));
        }
        return categoryDTOS;
    }
}
