package com.netcracker.sc.service;

import com.netcracker.sc.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO findById(Long categoryId);
    List<CategoryDTO> getDefaultCategories();
    List<CategoryDTO> getGroupCategories(Long groupId);
    Boolean addCategory(Long groupId, String categoryName);
}
