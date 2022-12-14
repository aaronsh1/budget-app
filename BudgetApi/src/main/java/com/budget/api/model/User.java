package com.budget.api.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "\"User\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId", nullable = false)
    private Integer id;

    @Column(name = "UserName")
    private String userName;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "emailAddress")
    private String emailAddress;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="UserBudget",
            joinColumns = { @JoinColumn(name = "\"User\"", referencedColumnName = "UserId")},
            inverseJoinColumns = { @JoinColumn( name = "Budget", referencedColumnName = "BudgetId")}
    )
    private Set<Budget> budgets;

    @OneToMany(mappedBy = "user")
    private Set<BudgetEntry> budgetEntries = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Account> accounts = new ArrayList<>();

    public User(String userName, String firstName, String lastName, String emailAddress) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
    }

    public User() {}

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public Set<BudgetEntry> getBudgetEntries() {
        return budgetEntries;
    }

    public void setBudgetEntries(Set<BudgetEntry> budgetEntries) {
        this.budgetEntries = budgetEntries;
    }

    public Set<Budget> getBudgets(){
        return this.budgets;
    }

    public void setBudgets(Set<Budget> budgets){
        this.budgets = budgets;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}