package com.netcracker.sc.service;

import com.netcracker.sc.dto.GraphCategoryDTO;
import com.netcracker.sc.dto.GraphLineCategoriesUserDTO;
import com.netcracker.sc.dto.GraphUsersDTO;

import java.util.List;

public interface GraphService {
    List<GraphCategoryDTO> getCategoriesForGraphByGroupId(Long id);

    List<GraphUsersDTO> getUsersForGraphBuGroupId(Long id);

    List<GraphCategoryDTO> getCategoriesForGraphByGroupIdAndUserId(Long groupId, Long userId);

    List<GraphLineCategoriesUserDTO> getCategoriesForLineGraphByGroupIdAndUserId(Long groupId, Long userId);
}
