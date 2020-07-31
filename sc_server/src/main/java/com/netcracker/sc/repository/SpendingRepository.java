package com.netcracker.sc.repository;

import com.netcracker.sc.domain.Group;
import com.netcracker.sc.domain.Spending;
import com.netcracker.sc.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SpendingRepository extends JpaRepository<Spending, Long> {
    @Transactional
    @Query(value = "select * from spending s where group_id = :groupId", nativeQuery = true)
    List<Spending> findByGroupId(@Param("groupId") Long groupId);
    Spending findBySpendingId(Long spendingId);
    @Transactional
    @Query(value = "select * from spending s where s.user_id = :userId and group_id = :groupId", nativeQuery = true)
    List<Spending> findByGroupIdAndUserId(@Param("groupId") Long groupId, @Param("userId") Long userId);
    @Modifying
    @Transactional
    void deleteSpendingBySpendingId(Long spendingId);
}
