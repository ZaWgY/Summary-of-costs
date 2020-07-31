package com.netcracker.sc.controller;

import com.netcracker.sc.domain.User;
import com.netcracker.sc.dto.LoginFormDTO;
import com.netcracker.sc.security.jwt.JwtTokenProvider;
import com.netcracker.sc.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/auth/")
@RestController
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserServiceImpl userService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserServiceImpl userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody LoginFormDTO requestDto) {
        try {
            String username = requestDto.getLogin();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByLogin(username);

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            String token = jwtTokenProvider.createToken(username);

            Map<Object, Object> response = new HashMap<>();
            response.put("id", user.getUserId());
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
