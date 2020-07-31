package com.netcracker.sc.repository;

import com.netcracker.sc.domain.Group;
import com.netcracker.sc.domain.GroupInvitation;
import com.netcracker.sc.domain.User;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GroupInvitationRepository extends JpaRepository<GroupInvitation, Long> {
    GroupInvitation findByGroupInvitationId(Long id);
    @Modifying
    @Transactional
    @Query(value = "select * from group_invitation gInv where gInv.is_deleted = false and gInv.user_id = :userId", nativeQuery = true)
    List<GroupInvitation> findByUserId(@Param("userId") Long userId);
    @Modifying
    @Transactional
    @Query(value = "update group_invitation set is_deleted = true where group_invitation_id = :invitationId", nativeQuery = true)
    void deleteInvitationById(@Param("invitationId") Long invitationId);
    GroupInvitation findByGroupAndUserAndIsDeleted(Group group, User user, Boolean isDeleted);
}
