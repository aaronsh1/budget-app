package com.budget.api.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccountId", nullable = false)
    private Integer id;

    @Column(name = "Balance")
    private Integer balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"User\"")
    private User user;

    @OneToMany(mappedBy = "account")
    private List<AccountEntry> accountEntries = new ArrayList<>();

    public Account(User user) {
        this.user = user;
        this.balance = 0;
    }

    public Account(){}

    public List<AccountEntry> getAccountEntries() {
        return accountEntries;
    }

    public void setAccountEntries(List<AccountEntry> accountEntries) {
        this.accountEntries = accountEntries;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}