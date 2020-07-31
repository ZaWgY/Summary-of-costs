package com.netcracker.sc.controller;

import com.netcracker.sc.dto.GraphCategoryDTO;
import com.netcracker.sc.dto.GraphLineCategoriesUserDTO;
import com.netcracker.sc.dto.GraphUsersDTO;
import com.netcracker.sc.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(value = "*")
public class GraphController {

    private GraphService graphService;

    @Autowired
    public GraphController(GraphService graphService) {
        this.graphService = graphService;
    }

    @GetMapping(value = "/graph/categories/{group_id}")
    public ResponseEntity<List<GraphCategoryDTO>> getCategoriesForGraph(@PathVariable(value = "group_id") Long id) {
        return ResponseEntity.ok(this.graphService.getCategoriesForGraphByGroupId(id));
    }

    @GetMapping(value = "/graph/users/{group_id}")
    public ResponseEntity<List<GraphUsersDTO>> getUsersFroGraph(@PathVariable(value = "group_id") Long id) {
        return ResponseEntity.ok(this.graphService.getUsersForGraphBuGroupId(id));
    }

    @GetMapping(value = "/graph/categories/{group_id}/users/{user_id}")
    public ResponseEntity<List<GraphCategoryDTO>> getGroupCategoriesForGraph(@PathVariable(value = "group_id") Long groupId, @PathVariable(value = "user_id") Long userId) {
        return ResponseEntity.ok(this.graphService.getCategoriesForGraphByGroupIdAndUserId(groupId, userId));
    }

    @GetMapping(value = "/graph/line/categories/{group_id}/users/{user_id}")
    public ResponseEntity<List<GraphLineCategoriesUserDTO>>  getGroupCategoriesForLineGraph (@PathVariable(value = "group_id") Long groupId, @PathVariable(value = "user_id") Long userId) {
        return ResponseEntity.ok(this.graphService.getCategoriesForLineGraphByGroupIdAndUserId(groupId,userId));
    }

}
