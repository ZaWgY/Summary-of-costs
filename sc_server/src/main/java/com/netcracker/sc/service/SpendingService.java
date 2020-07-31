package com.netcracker.sc.service;

import com.netcracker.sc.dto.SpendingDTO;
import com.netcracker.sc.dto.SpendingRepresentDTO;

import java.util.List;

public interface SpendingService {
    List<SpendingRepresentDTO> findByGroupId(Long groupId);
    Boolean addSpending(SpendingDTO spendingDTO);
    List<SpendingRepresentDTO> findByGroupIdAndUserId(Long groupId, Long userId);
    Boolean deleteSpending(Long spendingId);
}
