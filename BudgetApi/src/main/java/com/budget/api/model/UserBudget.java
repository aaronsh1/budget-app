package com.budget.api.model;

import javax.persistence.*;

@Entity
public class UserBudget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserBudgetId", nullable = false)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "[User]")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Budget")
    private Budget budget;

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    //TODO Reverse Engineering! Migrate other columns to the entity
}