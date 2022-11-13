package com.budget.api.DTO;

import com.budget.api.model.Budget;
import com.budget.api.model.BudgetEntry;
import com.budget.api.model.User;

import java.util.ArrayList;
import java.util.List;


public class BudgetEntryDTO {

    private Integer user;

    private Integer budget;

    private Integer amount;

    public BudgetEntryDTO(Integer user, Integer budget, Integer amount) {
        this.user = user;
        this.budget = budget;
        this.amount = amount;
    }

    public static BudgetEntryDTO convertToDTO(BudgetEntry budgetEntry){
        return new BudgetEntryDTO(budgetEntry.getUser().getId(), budgetEntry.getBudget().getId(), budgetEntry.getAmount());
    }

    public static List<BudgetEntryDTO> convertToDTOList(List<BudgetEntry> budgetEntries){
        List<BudgetEntryDTO> budgetEntriesToReturn = new ArrayList<>();

        for(BudgetEntry entry : budgetEntries){
            budgetEntriesToReturn.add(convertToDTO(entry));
        }

        return budgetEntriesToReturn;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
