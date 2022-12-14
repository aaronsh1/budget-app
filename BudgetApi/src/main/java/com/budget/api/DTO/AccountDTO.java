package com.budget.api.DTO;


import com.budget.api.model.Account;
import com.budget.api.model.AccountEntry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AccountDTO {

    private Integer balance;

    private Integer user;

    private Set<AccountEntryDTO> accountEntries;

    public AccountDTO(){}

    public AccountDTO(Integer balance, Integer user, Set<AccountEntryDTO> accountEntries) {
        this.balance = balance;
        this.user = user;
        this.accountEntries = accountEntries;
    }

    public AccountDTO(Integer user){
        this.user = user;
    }

    public static AccountDTO convertToDTO(Account account){
        return new AccountDTO(account.getBalance(), account.getUser().getId(), AccountEntryDTO.convertToDTOList(account.getAccountEntries()));
    }

    public static Set<AccountDTO> convertToDTOList(List<Account> accounts){
        Set<AccountDTO> accountsToReturn = new HashSet<>();

        for(Account account : accounts){
            accountsToReturn.add(convertToDTO(account));
        }

        return accountsToReturn;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Set<AccountEntryDTO> getAccountEntries() {
        return accountEntries;
    }

    public void setAccountEntries(Set<AccountEntryDTO> accountEntries) {
        this.accountEntries = accountEntries;
    }
}
