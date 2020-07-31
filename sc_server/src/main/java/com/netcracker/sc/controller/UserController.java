package com.netcracker.sc.controller;

import com.netcracker.sc.dto.UserFormDTO;
import com.netcracker.sc.enums.GroupRole;
import com.netcracker.sc.service.GroupService;
import com.netcracker.sc.service.PhotoService;
import com.netcracker.sc.service.RegistrationService;
import com.netcracker.sc.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@RestController
@CrossOrigin(origins = "*")
public class UserController {

    private RegistrationService registrationService;
    private UserService userService;
    private GroupService groupService;
    @Autowired
    private PhotoService photoService;

    public UserController(RegistrationService registrationService, UserService userService, GroupService groupService) {
        this.registrationService = registrationService;
        this.userService = userService;
        this.groupService = groupService;
    }

    @PostMapping(path = "/registration")
    public ResponseEntity registration(@RequestBody UserFormDTO userFormDTO){
        Boolean result =this.registrationService.registerUser(userFormDTO);
        HttpStatus status = result? HttpStatus.CREATED: HttpStatus.CONFLICT;
        return ResponseEntity.status(status).build();
    }

    @PatchMapping(path = "/users/{user_id}/edit")
    public ResponseEntity edit(@PathVariable(value = "user_id") Long userId, @RequestBody UserFormDTO userFormDTO){
        return userService.edit(userId, userFormDTO) ? ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping(path = "/users/{user_id}")
    public ResponseEntity<UserFormDTO> getUserById(@PathVariable(value = "user_id") Long userId){
        return ResponseEntity.ok().body(userService.findByUserId(userId));
    }

    @GetMapping(path = "/users/{user_id}/groups/roles")
    public ResponseEntity getGroupsForUserByRole(@PathVariable("user_id") Long userId, @RequestParam GroupRole groupRole){
        return ResponseEntity.ok().body(groupService.getGroupsForUserByRole(userId,groupRole));
    }

    @PostMapping(path = "/users/{user_id}/photo")
    public ResponseEntity savePhoto(@RequestParam(value = "file") MultipartFile photo, @PathVariable(value = "user_id") Long userId){
        log.warn(photo);
        photoService.savePhoto(photo, userId);
        return  ResponseEntity.ok().build();
    }

    @GetMapping(path = "/users/{user_id}/photo")
    public ResponseEntity getPhoto(@PathVariable(value = "user_id") Long userId){
        return  ResponseEntity.ok().body(photoService.getPhoto(userId));
    }

    @GetMapping(path = "/users")
    public ResponseEntity getUsers(){
        return ResponseEntity.ok().body(userService.getUsers());
    }

}
