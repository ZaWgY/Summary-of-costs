package com.netcracker.sc.controller;

import com.netcracker.sc.domain.Group;
import com.netcracker.sc.dto.GroupCreationFormDTO;
import com.netcracker.sc.dto.GroupDTO;
import com.netcracker.sc.dto.RoleAssignmentDTO;
import com.netcracker.sc.dto.UserDTO;
import com.netcracker.sc.enums.GroupRole;
import com.netcracker.sc.service.GroupService;
import com.netcracker.sc.service.PhotoService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Log4j2
@RestController
@CrossOrigin(value = "*")
public class GroupController {

    private GroupService groupService;
    private PhotoService photoService;

    public GroupController(GroupService groupService, PhotoService photoService) {
        this.groupService = groupService;
        this.photoService = photoService;
    }

    @PostMapping(path = "/groups")
    public ResponseEntity<GroupDTO> createGroup(@RequestBody GroupCreationFormDTO form){
        try {
            GroupDTO result = groupService.createGroup(form);
            System.out.println(result);
            return result == null ? ResponseEntity.status(HttpStatus.BAD_REQUEST).build() : ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping(path = "/groups/{group_id}/edit")
    public ResponseEntity editGroup(@PathVariable(value = "group_id") Long groupId, @RequestBody GroupCreationFormDTO form){
        return groupService.editGroup(groupId, form) ? ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PatchMapping(path = "/groups/assignRole")
    public ResponseEntity assignRole(@RequestBody RoleAssignmentDTO roleAssignmentDTO){
        try {
            groupService.assignRole(roleAssignmentDTO);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(path = "/users/{user_id}/groups")
    public ResponseEntity<List<GroupDTO>> getGroupsByUserId(@PathVariable(value = "user_id") Long userId){
        return ResponseEntity.ok().body(groupService.getGroupsByUserId(userId));
    }

    @GetMapping(path = "/user/{user_id}/access/group/{group_id}")
    public ResponseEntity<Boolean> getAccessToGroup(@PathVariable(value = "user_id") Long userId, @PathVariable(value = "group_id") Long groupId) {
        return ResponseEntity.ok(this.groupService.checkUserAccess(userId,groupId));
    }

    @GetMapping(path = "/groups/{group_id}")
    public ResponseEntity getGroupInfoById(@PathVariable(value = "group_id")Long groupId){
        GroupDTO group = this.groupService.findGroupDtoById(groupId);
        return group != null? ResponseEntity.status(HttpStatus.OK).body(group): ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping(path = "/groups/{group_id}/roles/{user_id}")
    public ResponseEntity getUserRoleInGroup(@PathVariable(value = "user_id") Long userId, @PathVariable(value = "group_id") Long groupID) {
        GroupRole result = this.groupService.getUserRoleInGroupByUserIdAndGroupId(userId,groupID);
        return result != null? ResponseEntity.ok().body(result) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping(path = "/groups/{group_id}/photo")
    public ResponseEntity savePhoto(@RequestParam(value = "file") MultipartFile photo, @PathVariable(value = "group_id") Long groupId){
        log.warn(photo);
        photoService.savePhotoForGroup(photo, groupId);
        return  ResponseEntity.ok().build();
    }

    @GetMapping(path = "/groups/{group_id}/photo")
    public ResponseEntity getPhoto(@PathVariable(value = "group_id") Long groupId){
        return  ResponseEntity.ok().body(photoService.getPhotoForGroup(groupId));
    }

    @DeleteMapping(path = "/groups/{group_id}/users/{user_id}")
    public ResponseEntity deleteMember(@PathVariable(value = "group_id")Long groupId, @PathVariable(value = "user_id")Long userId){
        return groupService.deleteMember(groupId, userId) ? ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
