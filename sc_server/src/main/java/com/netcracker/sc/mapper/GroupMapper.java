package com.netcracker.sc.mapper;

import com.netcracker.sc.domain.Group;
import com.netcracker.sc.dto.GroupCreationFormDTO;
import com.netcracker.sc.dto.GroupDTO;
import com.netcracker.sc.dto.GroupListDto;
import com.netcracker.sc.dto.UserDTO;
import com.netcracker.sc.repository.UserRepository;
import com.netcracker.sc.service.GroupService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
public class GroupMapper {

    UserRepository userRepository;


    public GroupMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    public Group toEntity(GroupDTO groupDTO){
//        Group result = new Group();
//        return result;
//    }

    public GroupListDto toGroupListDTO(Group group){
        GroupListDto result = new GroupListDto();
        result.setGroupId(group.getGroupId());
        result.setName(group.getName());
        result.setDescription(group.getDescription());
        return result;
    }

    public GroupDTO toDTO(Group group, List<UserDTO> usersByGroupId){
        GroupDTO result = new GroupDTO();
        result.setGroupId(group.getGroupId());
        result.setName(group.getName());
        result.setDescription(group.getDescription());
        result.setMaxAmount(group.getMaxAmount());
        result.setCurrentAmount(group.getCurrentAmount());
        result.setListOfUsers(usersByGroupId);
        result.setImgName(group.getImgName());
        if(group.getCurrentAmount() == null || group.getCurrentAmount().equals(new BigDecimal(0))) {
            result.setProgress(BigDecimal.ZERO);
        }
        else {
            result.setProgress(new BigDecimal(100).multiply(group.getCurrentAmount().divide(group.getMaxAmount(), RoundingMode.FLOOR)));
        }
        return result;
    }

    public Group toEntity(GroupCreationFormDTO form){
        if (userRepository.findByUserId(form.getCreatorId()) == null){
            log.error("There is no user with such id: " + form.getCreatorId());
            throw new IllegalArgumentException("There is no user with such id: " + form.getCreatorId());
        }
        Group result = new Group();
        result.setName(form.getName());
        result.setDescription(form.getDescription());
        result.setMaxAmount(form.getMaxAmount());
        result.setCurrentAmount(new BigDecimal(0));
        return result;
    }

    public List<GroupDTO> toDTOs(List<Group> groups){
        List<GroupDTO> result = new ArrayList<>();
        for (Group group:
                groups) {
            result.add(toDTO(group ,null)); //spit
        }
        return result;
    }
}
