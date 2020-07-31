package com.netcracker.sc.service;

import com.netcracker.sc.domain.Category;
import com.netcracker.sc.domain.Group;
import com.netcracker.sc.dto.CategoryDTO;
import com.netcracker.sc.mapper.CategoryMapper;
import com.netcracker.sc.repository.CategoryRepository;
import com.netcracker.sc.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private GroupRepository groupRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, GroupRepository groupRepository) {
        this.categoryRepository = categoryRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public CategoryDTO findById(Long categoryId) {
        return CategoryMapper.toDTO(categoryRepository.findByCategoryId(categoryId));
    }

    @Override
    public List<CategoryDTO> getDefaultCategories() {
        return CategoryMapper.toListDTO(this.categoryRepository.findByIsDefault(true));
    }

    @Override
    public List<CategoryDTO> getGroupCategories(Long groupId) {
        Group group = this.groupRepository.findByGroupId(groupId);
        List<CategoryDTO> list =  CategoryMapper.toListDTO(this.categoryRepository.findByGroup(group));
        list.addAll(CategoryMapper.toListDTO(this.categoryRepository.findByIsDefault(true)));
        return list;
    }

    @Override
    public Boolean addCategory(Long groupId, String categoryName) {
        Group group = this.groupRepository.findByGroupId(groupId);
        if( this.categoryRepository.findAllByGroupAndName(group, categoryName).size() != 0) {
            return false;
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setGroup(group);
        this.categoryRepository.save(category);
        return true;
    }
}
