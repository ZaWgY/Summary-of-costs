package com.netcracker.sc.service;

import com.netcracker.sc.domain.User;
import com.netcracker.sc.dto.UserDTO;
import com.netcracker.sc.dto.UserFormDTO;
import com.netcracker.sc.mapper.UserMapper;
import com.netcracker.sc.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public User findByLogin(String username) {
        return this.userRepository.findByLogin(username);
    }

    @Transactional
    @Override
    public Boolean edit(Long id, UserFormDTO form) {
        try {
            User result = userRepository.findByUserId(id);
            if (!StringUtils.isEmpty(form.getEmail())){
                result.setEmail(form.getEmail());
            }
            if (!StringUtils.isEmpty(form.getFirstName())){
                result.setFirstName(form.getFirstName());
            }
            if (!StringUtils.isEmpty(form.getSecondName())){
                result.setSecondName(form.getSecondName());
            }
            if (!StringUtils.isEmpty(form.getLogin())){
                result.setLogin(form.getLogin());
            }
            if (!StringUtils.isEmpty(form.getPassword())){
                result.setPassword(passwordEncoder.encode(form.getPassword()));
            }
            userRepository.save(result);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public UserFormDTO findByUserId(Long userId) {
        return userMapper.userToDTO(userRepository.findByUserId(userId));
    }

    @Override
    public List<UserDTO> getUsers() {
        return userMapper.toEntities(userRepository.findAll());
    }
}
