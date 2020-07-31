package com.netcracker.sc.controller;


import com.netcracker.sc.dto.CategoryDTO;
import com.netcracker.sc.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(value = "*")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(path = "/category/default")
    public ResponseEntity<List<CategoryDTO>> getDefaultEntities() {
        return ResponseEntity.ok(this.categoryService.getDefaultCategories());
    }

    @GetMapping(path = "/category/{category_id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable(name = "category_id") Long id) {
        return ResponseEntity.ok(this.categoryService.findById(id));
    }

    @GetMapping(path = "/category/group/{group_id}")
    public ResponseEntity<List<CategoryDTO>> getGroupsCategories(@PathVariable(name = "group_id") Long groupId) {
        return ResponseEntity.ok(this.categoryService.getGroupCategories(groupId));
    }

    @PostMapping(path = "/category/{group_id}")
    public ResponseEntity addCustomCategory(@RequestBody String categoryName, @PathVariable(value = "group_id") Long groupId) {
        return ResponseEntity.ok(this.categoryService.addCategory(groupId, categoryName));
    }
}
