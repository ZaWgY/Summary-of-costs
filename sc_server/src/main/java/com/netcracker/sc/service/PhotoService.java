package com.netcracker.sc.service;

import com.netcracker.sc.domain.Group;
import com.netcracker.sc.domain.User;
import com.netcracker.sc.repository.GroupRepository;
import com.netcracker.sc.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Log4j2
@Service
public class PhotoService {
    private UserRepository userRepository;
    private GroupRepository groupRepository;

    public PhotoService(UserRepository userRepository, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    public void savePhoto(MultipartFile file, Long userId){
        final String location = "C:\\Games\\images\\";
        String fileName = file.getOriginalFilename();
        log.info("saving file " + fileName);
        File pathFile = new File(location + fileName);
        try {
            file.transferTo(pathFile);
            User user = userRepository.findByUserId(userId);
            user.setImgName(fileName);
            userRepository.save(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void savePhotoForGroup(MultipartFile file, Long groupId){
        final String location = "C:\\Games\\images\\";
        String fileName = file.getOriginalFilename();
        log.info("saving file" + fileName);
        File pathFile = new File(location + fileName);
        try {
            file.transferTo(pathFile);
            Group group = groupRepository.findByGroupId(groupId);
            group.setImgName(fileName);
            groupRepository.save(group);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPhoto(Long userId){
        return userRepository.findByUserId(userId).getImgName();
    }

    public String getPhotoForGroup(Long groupId){
        return groupRepository.findByGroupId(groupId).getImgName();
    }
}
