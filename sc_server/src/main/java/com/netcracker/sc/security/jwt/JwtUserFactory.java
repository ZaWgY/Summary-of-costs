package com.netcracker.sc.security.jwt;

import com.netcracker.sc.domain.User;

import java.util.ArrayList;

public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getUserId(),
                user.getLogin(),
                user.getFirstName(),
                user.getSecondName(),
                user.getEmail(),
                user.getPassword()
        );
    }


}