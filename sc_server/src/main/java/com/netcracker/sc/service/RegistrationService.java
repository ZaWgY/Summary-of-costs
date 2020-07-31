package com.netcracker.sc.service;

import com.netcracker.sc.domain.User;
import com.netcracker.sc.dto.UserFormDTO;
import com.netcracker.sc.mapper.UserMapper;
import com.netcracker.sc.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class RegistrationService {
    private UserRepository userRepository;
    private UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private JavaMailSender javaMailSender;

    @Autowired
    public RegistrationService(JavaMailSender javaMailSender, UserRepository userRepository, UserMapper userMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.javaMailSender = javaMailSender;
    }

    public Boolean registerUser(UserFormDTO userFormDTO) {
        try {
            User user = userMapper.dtoToUser(userFormDTO);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            new Thread(()->{
                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                simpleMailMessage.setTo(user.getEmail());
                simpleMailMessage.setFrom("hogwartssocialnetwork@gmail.com");
                simpleMailMessage.setSubject("Регистрация на Summary of costs");
                simpleMailMessage.setText("Здравствуйте "+user.getFirstName()+","+'\n'+"Вы были зарегистрированы на лучшем ресурсе в интернете.");
                javaMailSender.send(simpleMailMessage);
            }).run();
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
