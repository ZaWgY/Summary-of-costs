package com.netcracker.sc.repository;

import com.netcracker.sc.domain.Group;
import com.netcracker.sc.domain.User;
import com.netcracker.sc.domain.UserToGroup;
import com.netcracker.sc.enums.GroupRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

public interface UserToGroupRepository extends JpaRepository<UserToGroup, Long> {
    UserToGroup findByUser(User user);
    UserToGroup findByUserAndGroup(User user, Group group);
    List<UserToGroup> findByGroup(Group group);

    @Modifying
    @Transactional
    @Query(value = "update UserToGroup set groupRole = :groupRole where user = :user and group = :group")
    void updateRole(@Param("groupRole") GroupRole groupRole, @Param("user") User user, @Param("group") Group group);
    @Query("SELECT u FROM UserToGroup u WHERE u.group = :group")
    Collection<UserToGroup> findAllUsersByGroup(@Param("group") Group group);
    List<UserToGroup> findByUserAndGroupRole(User user, GroupRole groupRole);
    List<UserToGroup> findAllByGroupAndGroupRole(Group group, GroupRole groupRole);
    @Modifying
    @Transactional
    @Query("delete from UserToGroup where user = :user and group = :group")
    void deleteMember(@Param("user") User user, @Param("group") Group group);
}
