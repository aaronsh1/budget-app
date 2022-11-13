package com.budget.api.service;

import com.budget.api.DTO.BudgetDTO;
import com.budget.api.DTO.BudgetEntryDTO;
import com.budget.api.exception.ResourceNotFoundException;

public interface BudgetService {

    BudgetDTO getBudget(int budgetId) throws ResourceNotFoundException;

    BudgetDTO addBudget(BudgetDTO budgetDTOToAdd);

    BudgetDTO updateBudget(int budgetId, BudgetDTO budgetDTO) throws ResourceNotFoundException;

    BudgetDTO replaceBudget(int budgetId, BudgetDTO budgetDTO) throws ResourceNotFoundException;

    BudgetEntryDTO addEntryToBudget(int budgetId, BudgetEntryDTO budgetEntryDTO) throws ResourceNotFoundException;

    void deleteBudget(int budgetId);
}
