package com.netcracker.sc.service;

import com.netcracker.sc.domain.User;
import com.netcracker.sc.dto.UserDTO;
import com.netcracker.sc.dto.UserFormDTO;

import java.util.List;

public interface UserService {
    User findByLogin(String username);
    List<UserDTO> getUsers();
    UserFormDTO findByUserId(Long userId);
    Boolean edit(Long id, UserFormDTO form);
}
