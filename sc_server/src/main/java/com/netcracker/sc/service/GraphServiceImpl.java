package com.netcracker.sc.service;

import com.netcracker.sc.domain.Spending;
import com.netcracker.sc.dto.GraphCategoryDTO;
import com.netcracker.sc.dto.GraphLineCategoriesUserDTO;
import com.netcracker.sc.dto.GraphUsersDTO;
import com.netcracker.sc.repository.GroupRepository;
import com.netcracker.sc.repository.SpendingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GraphServiceImpl implements GraphService {

    private GroupRepository groupRepository;
    private SpendingRepository spendingRepository;

    @Autowired
    public GraphServiceImpl(GroupRepository groupRepository, SpendingRepository spendingRepository) {
        this.groupRepository = groupRepository;
        this.spendingRepository = spendingRepository;
    }

    @Override
    public List<GraphCategoryDTO> getCategoriesForGraphByGroupId(Long id) {
        List<GraphCategoryDTO> finalResult = new ArrayList<>();
        List<Spending> spendings = this.spendingRepository.findByGroupId(id);
        HashMap<String,BigDecimal> result = new HashMap<>();
        for (Spending s:
             spendings) {
            if (result.containsKey(s.getCategory().getName())) {
                result.put(s.getCategory().getName(),result.get(s.getCategory().getName()).add(s.getAmount()));
            } else {
                result.put(s.getCategory().getName(),s.getAmount());
            }
        }
        for (Map.Entry<String, BigDecimal> entry:
             result.entrySet()) {
            GraphCategoryDTO categoryDTO = new GraphCategoryDTO();
            categoryDTO.setCategoryName(entry.getKey());
            categoryDTO.setAmount(entry.getValue());
            finalResult.add(categoryDTO);
        }
        return finalResult;
    }

    @Override
    public List<GraphCategoryDTO> getCategoriesForGraphByGroupIdAndUserId(Long groupId, Long userId) {
        List<GraphCategoryDTO> finalResult = new ArrayList<>();
        List<Spending> spendings = this.spendingRepository.findByGroupIdAndUserId(groupId,userId);
        HashMap<String,BigDecimal> result = new HashMap<>();
        for (Spending s:
                spendings) {
            if (result.containsKey(s.getCategory().getName())) {
                result.put(s.getCategory().getName(),result.get(s.getCategory().getName()).add(s.getAmount()));
            } else {
                result.put(s.getCategory().getName(),s.getAmount());
            }
        }
        for (Map.Entry<String, BigDecimal> entry:
                result.entrySet()) {
            GraphCategoryDTO categoryDTO = new GraphCategoryDTO();
            categoryDTO.setCategoryName(entry.getKey());
            categoryDTO.setAmount(entry.getValue());
            finalResult.add(categoryDTO);
        }
        return finalResult;
    }

    @Override
    public List<GraphLineCategoriesUserDTO> getCategoriesForLineGraphByGroupIdAndUserId(Long groupId, Long userId) {
        List<GraphLineCategoriesUserDTO> finalResult = new ArrayList<>();
        List<Spending> spendings = this.spendingRepository.findByGroupIdAndUserId(groupId,userId);
        HashMap<String, List<BigDecimal>> result = new HashMap<>();
        for (Spending s:
                spendings) {
            List<BigDecimal> list;
            if (result.containsKey(s.getCategory().getName())) {
                list = result.get(s.getCategory().getName());
                list.add(s.getAmount());
                result.put(s.getCategory().getName(),list);
            } else {
                list = new ArrayList<>();
                list.add(s.getAmount());
                result.put(s.getCategory().getName(),list);
            }
        }
        for (Map.Entry<String, List<BigDecimal>> entry:
                result.entrySet()) {
            GraphLineCategoriesUserDTO graphLineCategoriesUserDTO = new GraphLineCategoriesUserDTO();
            graphLineCategoriesUserDTO.setCategoryName(entry.getKey());
            graphLineCategoriesUserDTO.setSpendings(entry.getValue());
            finalResult.add(graphLineCategoriesUserDTO);
        }
        return finalResult;

    }

    @Override
    public List<GraphUsersDTO> getUsersForGraphBuGroupId(Long id) {
        List<GraphUsersDTO> finalResult = new ArrayList<>();
        List<Spending> spendings = this.spendingRepository.findByGroupId(id);
        HashMap<String,BigDecimal> result = new HashMap<>();
        for (Spending s:
                spendings) {
            if (result.containsKey(s.getCategory().getName())) {
                result.put(s.getUser().getLogin(),result.get(s.getUser().getLogin()).add(s.getAmount()));
            } else {
                result.put(s.getUser().getLogin(),s.getAmount());
            }
        }
        for (Map.Entry<String, BigDecimal> entry:
                result.entrySet()) {
            GraphUsersDTO categoryDTO = new GraphUsersDTO();
            categoryDTO.setLogin(entry.getKey());
            categoryDTO.setAmount(entry.getValue());
            finalResult.add(categoryDTO);
        }
        return finalResult;
    }


}
