package com.netcracker.sc.controller;

import com.netcracker.sc.domain.Group;
import com.netcracker.sc.domain.User;
import com.netcracker.sc.domain.UserToGroup;
import com.netcracker.sc.repository.GroupRepository;
import com.netcracker.sc.repository.UserRepository;
import com.netcracker.sc.repository.UserToGroupRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Log4j2
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/hello")
public class HelloController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserToGroupRepository userToGroupRepository;
    @Autowired
    GroupRepository groupRepository;

    @GetMapping
    public ResponseEntity hello(){
//        User user = new User();
//        Group group = new Group();
//        groupRepository.save(group);
//        List<UserToGroup> list = new ArrayList<>();
//        //list.add(new UserToGroup(user,group));
//        user.setUserToGroupSet(list);
//        userRepository.save(user);
        //userToGroupRepository.save(new UserToGroup(userRepository.findByUserId(new Long(1)),groupRepository.findByGroupId(new Long(1))));
//        List<UserToGroup> set = new ArrayList<>();
//        set.add(new UserToGroup(user,group));
//        user.setUserToGroupSet(set);
       // userRepository.save(user);
       User user = userRepository.findByUserId(new Long(1));
       UserToGroup userToGroup = userToGroupRepository.findByUser(user);
       //log.warn(userToGroup.getUser().getUserId());
       return ResponseEntity.ok().body(userToGroup.getUser());
    }
}
