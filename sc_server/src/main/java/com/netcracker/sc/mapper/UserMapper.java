package com.netcracker.sc.mapper;

import com.netcracker.sc.domain.User;
import com.netcracker.sc.dto.LoginFormDTO;
import com.netcracker.sc.dto.UserDTO;
import com.netcracker.sc.dto.UserFormDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {

    public User dtoToUser(UserFormDTO registrationFormDTO) {
        User user = new User();
        user.setFirstName(registrationFormDTO.getFirstName());
        user.setSecondName(registrationFormDTO.getSecondName());
        user.setPassword(registrationFormDTO.getPassword());
        user.setEmail(registrationFormDTO.getEmail());
        user.setLogin(registrationFormDTO.getLogin());
        return user;
    }

    public UserFormDTO userToDTO(User user) {
        UserFormDTO registrationFormDTO = new UserFormDTO();
        registrationFormDTO.setFirstName(user.getFirstName());
        registrationFormDTO.setSecondName(user.getSecondName());
        registrationFormDTO.setEmail(user.getEmail());
        registrationFormDTO.setLogin(user.getLogin());
        registrationFormDTO.setPassword(user.getPassword());
        return registrationFormDTO;
    }

    public LoginFormDTO userToLoginDto(User user) {
        LoginFormDTO loginFormDTO = new LoginFormDTO();
        loginFormDTO.setLogin(user.getLogin());
        loginFormDTO.setPassword(user.getPassword());
        return loginFormDTO;
    }

    public User loginDTOtoUser(LoginFormDTO loginFormDTO) {
        User user = new User();
        user.setLogin(loginFormDTO.getLogin());
        user.setPassword(loginFormDTO.getPassword());
        return user;
    }

    public List<UserDTO> toEntities(List<User> users) {
        ArrayList<UserDTO> userDTOS = new ArrayList<>();
        for (User u:
             users) {
            UserDTO currentUserDTO = new UserDTO();
            currentUserDTO.setLogin(u.getLogin());
            currentUserDTO.setFirstName(u.getFirstName());
            currentUserDTO.setSecondName(u.getSecondName());
            currentUserDTO.setImgName(u.getImgName());
            currentUserDTO.setId(u.getUserId().toString());
            userDTOS.add(currentUserDTO);
        }
        return userDTOS;
    }
}
