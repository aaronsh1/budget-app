package com.budget.api.DTO;

import com.budget.api.model.Budget;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

public class BudgetDTO {

    private Integer goal;

    private Integer saved;

    public BudgetDTO(Integer goal, Integer saved) {
        this.goal = goal;
        this.saved = saved;
    }

    public BudgetDTO(){}

    public static BudgetDTO convertToDTO(Budget budget){
        return new BudgetDTO(budget.getGoal(), budget.getSaved());
    }

    public static List<BudgetDTO> convertToDTOList(List<Budget> budgets){
        List<BudgetDTO> budgetDTOs = new ArrayList<BudgetDTO>();

        for(Budget budget : budgets){
            budgetDTOs.add(convertToDTO(budget));
        }

        return budgetDTOs;
    }

    public Integer getGoal() {
        return goal;
    }

    public void setGoal(Integer goal) {
        this.goal = goal;
    }

    public Integer getSaved() {
        return saved;
    }

    public void setSaved(Integer saved) {
        this.saved = saved;
    }
}
