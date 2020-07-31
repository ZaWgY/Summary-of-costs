package com.netcracker.sc.repository;

import com.netcracker.sc.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(Long id);
    User findByLogin(String login);
}
