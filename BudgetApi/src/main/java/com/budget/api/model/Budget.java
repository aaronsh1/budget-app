package com.budget.api.model;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Budget")
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BudgetId", nullable = false)
    private Integer id;

    @Column(name = "Goal")
    private Integer goal;

    @Column(name = "Saved")
    private Integer saved;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "UserBudget",
            joinColumns = { @JoinColumn(name = "Budget", referencedColumnName = "BudgetId") },
            inverseJoinColumns = { @JoinColumn(name = "\"User\"", referencedColumnName = "UserId") }
    )
    Set<User> users;

    @OneToMany(mappedBy = "budget")
    private Set<BudgetEntry> budgetEntries = new LinkedHashSet<>();

    public Budget(Integer goal, Set<User> users) {
        this.goal = goal;
        this.users = users;
        this.saved = 0;
    }

    public Budget(Integer goal, Integer saved) {
        this.goal = goal;
        this.saved = saved;
    }

    public Budget(){}

    public Set<BudgetEntry> getBudgetEntries() {
        return budgetEntries;
    }

    public void setBudgetEntries(Set<BudgetEntry> budgetEntries) {
        this.budgetEntries = budgetEntries;
    }

    public Set<User> getUsers(){
        return this.users;
    }

    public void setUsers(Set<User> users){
        this.users = users;
    }

//    public Set<UserBudget> getUserBudgets() {
//        return userBudgets;
//    }
//
//    public void setUserBudgets(Set<UserBudget> userBudgets) {
//        this.userBudgets = userBudgets;
//    }

    public Integer getSaved() {
        return saved;
    }

    public void setSaved(Integer saved) {
        this.saved = saved;
    }

    public Integer getGoal() {
        return goal;
    }

    public void setGoal(Integer goal) {
        this.goal = goal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}