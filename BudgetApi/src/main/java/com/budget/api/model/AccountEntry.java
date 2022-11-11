package com.budget.api.model;

import com.budget.api.DTO.AccountEntryDTO;

import javax.persistence.*;

@Entity
@Table(name = "AccountEntry")
public class AccountEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccountEntryId", nullable = false)
    private Integer id;

    @Column(name = "Amount")
    private Integer amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Account")
    private Account account;

    @Column(name = "Type", length = 1)
    private String type;

    public AccountEntry(Integer amount, Account account, String type) {
        this.amount = amount;
        this.account = account;
        this.type = type;
    }

    public AccountEntry() {}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}