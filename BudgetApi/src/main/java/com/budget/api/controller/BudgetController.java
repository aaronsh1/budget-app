package com.budget.api.controller;

import com.budget.api.DTO.BudgetDTO;
import com.budget.api.DTO.BudgetEntryDTO;
import com.budget.api.exception.EmptyResourceException;
import com.budget.api.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/v1/budget")
public class BudgetController {
    
    @Autowired
    private BudgetService budgetService;

    @GetMapping("/{budget-id}")
    public BudgetDTO getBudget(@PathVariable("budget-id") int budgetId){
        return budgetService.getBudget(budgetId);
    }

    @PostMapping
    public BudgetDTO addBudget(@RequestBody BudgetDTO budgetDTOToAdd){
        return budgetService.addBudget(budgetDTOToAdd);
    }

    @PatchMapping("/{budget-id}")
    public BudgetDTO updateBudget(@PathVariable("budget-id") int budgetId, @RequestBody BudgetDTO budgetDTO){
        return budgetService.updateBudget(budgetId, budgetDTO);
    }

    @PutMapping("/{budget-id}")
    public BudgetDTO replaceBudget(@PathVariable("budget-id") int budgetId, @RequestBody BudgetDTO budgetDTO) throws EmptyResourceException {
        return budgetService.replaceBudget(budgetId, budgetDTO);
    }

    @PostMapping("/{budget-id}/transaction")
    @Transactional
    public BudgetEntryDTO addEntryToBudget(@PathVariable("budget-id") int budgetId, @RequestBody BudgetEntryDTO budgetEntryDTO){
        return budgetService.addEntryToBudget(budgetId, budgetEntryDTO);
    }

    @DeleteMapping("/{budget-id}")
    public String deleteBudget(@PathVariable("budget-id") int budgetId){
            budgetService.deleteBudget(budgetId);
            return "{\"Budget Successfully Deleted.\"}";
    }
}












