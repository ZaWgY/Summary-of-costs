package com.netcracker.sc.mapper;

import com.netcracker.sc.domain.Spending;
import com.netcracker.sc.dto.SpendingDTO;
import com.netcracker.sc.dto.SpendingRepresentDTO;
import com.netcracker.sc.repository.CategoryRepository;
import com.netcracker.sc.repository.GroupRepository;
import com.netcracker.sc.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class SpendingMapper {

    private GroupRepository groupRepository;
    private CategoryRepository categoryRepository;
    private UserRepository userRepository;

    public SpendingMapper(GroupRepository groupRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public List<SpendingDTO> toDTOs(List<Spending> spendings){
        List<SpendingDTO> result = new ArrayList<>();
        for (Spending spending:
             spendings) {
            result.add(toDTO(spending));
        }
        return result;
    }

    public SpendingDTO toDTO(Spending spending){
        SpendingDTO result = new SpendingDTO();
        result.setAmount(spending.getAmount());
        result.setCategoryId(spending.getCategory().getCategoryId());
        result.setGroupId(spending.getGroup().getGroupId());
        result.setUserId(spending.getUser().getUserId());
        return result;
    }

    public Spending toEntity(SpendingDTO spendingDTO){
        Spending result = new Spending();
        result.setAmount(spendingDTO.getAmount());
        result.setCategory(categoryRepository.findByCategoryId(spendingDTO.getCategoryId()));
        result.setGroup(groupRepository.findByGroupId(spendingDTO.getGroupId()));
        result.setUser(userRepository.findByUserId(spendingDTO.getUserId()));
        result.setDate(new Date());
        return result;
    }

    public SpendingRepresentDTO toRespresentDTO(Spending spending) {
        SpendingRepresentDTO spendingRepresentDTO = new SpendingRepresentDTO();
        spendingRepresentDTO.setId(spending.getSpendingId());
        spendingRepresentDTO.setAmount(spending.getAmount());
        spendingRepresentDTO.setCategoryName(spending.getCategory().getName());
        spendingRepresentDTO.setLogin(spending.getUser().getLogin());
        spendingRepresentDTO.setDate(spending.getDate().toString().substring(0,10));
        return spendingRepresentDTO;
    }

    public List<SpendingRepresentDTO> toRepresentDTOS(List<Spending> spendings) {
        List<SpendingRepresentDTO> result = new ArrayList<>();
        for (Spending spending:
                spendings) {
            result.add(toRespresentDTO(spending));
        }
        Collections.reverse(result);
        return result;
    }
}
