package com.netcracker.sc.controller;

import com.netcracker.sc.dto.SpendingDTO;
import com.netcracker.sc.dto.SpendingRepresentDTO;
import com.netcracker.sc.service.SpendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(value = "*")
public class SpendingController {

    private SpendingService spendingService;

    @Autowired
    public SpendingController(SpendingService spendingService) {
        this.spendingService = spendingService;
    }

    @GetMapping(path = "/groups/{group_id}/spendings")
    public ResponseEntity<List<SpendingRepresentDTO>> getSpendingsByGroupId(@PathVariable(value = "group_id") Long groupId){
        return ResponseEntity.ok().body(spendingService.findByGroupId(groupId));
    }

    @GetMapping(path = "/groups/{group_id}/users/{user_id}/spendings")
    public ResponseEntity<List<SpendingRepresentDTO>> getSpendingsByGroupIdAndUserId(@PathVariable(value = "group_id") Long groupId, @PathVariable(value = "user_id") Long userId){
        return ResponseEntity.ok().body(spendingService.findByGroupIdAndUserId(groupId, userId));
    }

    @PostMapping(path = "/spendings")
    public ResponseEntity addSpending(@RequestBody SpendingDTO spendingDTO){
        return spendingService.addSpending(spendingDTO) ? ResponseEntity.status(HttpStatus.CREATED).build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping(path = "/spendings/{spending_id}")
    public ResponseEntity deleteSpending(@PathVariable(value = "spending_id") Long spendingId){
        return spendingService.deleteSpending(spendingId) ? ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
