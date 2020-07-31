package com.netcracker.sc.repository;

import com.netcracker.sc.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Group findByGroupId(Long id);
}
