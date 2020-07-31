package com.netcracker.sc.repository;

import com.netcracker.sc.domain.Category;
import com.netcracker.sc.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryId(Long id);
    List<Category> findByGroup(Group group);
    List<Category> findByIsDefault(Boolean bool);
    List<Category> findAllByGroupAndName(Group group, String name);

}
