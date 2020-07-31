package com.netcracker.sc.controller;

import com.netcracker.sc.dto.GroupInvitationDTO;
import com.netcracker.sc.service.GroupInvitationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(value = "*")
public class GroupInvitationController {

    private GroupInvitationService groupInvitationService;

    public GroupInvitationController(GroupInvitationService groupInvitationService) {
        this.groupInvitationService = groupInvitationService;
    }

    @PostMapping(path = "/groups/{group_id}/invitations")
    public ResponseEntity invite(@PathVariable(value = "group_id") Long groupId, @RequestBody String login){
        try {
            return groupInvitationService.addInvitation(groupId,login) ? ResponseEntity.status(HttpStatus.CREATED).build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(path = "/users/{user_id}/invitations")
    public ResponseEntity<List<GroupInvitationDTO>> getInvitationsByUserId(@PathVariable(value = "user_id") Long userId){
        return ResponseEntity.ok().body(groupInvitationService.findByUserId(userId));
    }

    @PostMapping(path = "/invitations/accept")
    public ResponseEntity acceptInvite(@RequestBody GroupInvitationDTO groupInvitationDTO){
        try {
            return groupInvitationService.acceptInvitation(groupInvitationDTO)
                    ? ResponseEntity.status(HttpStatus.OK).build()
                    : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping(path = "/invitations/decline")
    public ResponseEntity declineInvite(@RequestBody GroupInvitationDTO groupInvitationDTO){
        try {
            return groupInvitationService.declineInvitation(groupInvitationDTO)
                    ? ResponseEntity.ok().build()
                    : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
