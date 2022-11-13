package com.budget.api.DTO;

import com.budget.api.model.Budget;
import com.budget.api.model.User;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BudgetDTO {

    private Set<Integer> users;

    private Integer goal;

    private Integer saved;

    public BudgetDTO(Integer goal, Integer saved, Set<Integer> users) {
        this.users = users;
        this.goal = goal;
        this.saved = saved;
    }

    public BudgetDTO(Budget budget){}

    public static BudgetDTO convertToDTO(Budget budget){
        Set<Integer> userIds = new HashSet<>();
        if(budget.getUsers() != null){
            for(User user : budget.getUsers())
                userIds.add(user.getId());
        }

        return new BudgetDTO(budget.getGoal(), budget.getSaved(), userIds);
    }

    public static Set<BudgetDTO> convertToDTOList(Set<Budget> budgets){
        Set<BudgetDTO> budgetDTOs = new HashSet<>();

        for(Budget budget : budgets){
            budgetDTOs.add(convertToDTO(budget));
        }

        return budgetDTOs;
    }

    public static Budget toBudget(BudgetDTO budgetDTO, Set<User> users){
        return new Budget(budgetDTO.getGoal(), users);
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

    public Set<Integer> getUsers() {
        return users;
    }

    public void setUsers(Set<Integer> user) {
        this.users = user;
    }
}
