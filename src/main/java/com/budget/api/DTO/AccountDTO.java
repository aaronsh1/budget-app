package com.budget.api.DTO;


import com.budget.api.model.Account;
import com.budget.api.model.AccountEntry;

import java.util.ArrayList;
import java.util.List;

public class AccountDTO {

    private Integer balance;

    private Integer user;

    private List<AccountEntryDTO> accountEntries;

    public AccountDTO(){}

    public AccountDTO(Integer balance, Integer user, List<AccountEntryDTO> accountEntries) {
        this.balance = balance;
        this.user = user;
        this.accountEntries = accountEntries;
    }

    public static AccountDTO convertToDTO(Account account){
        return new AccountDTO(account.getBalance(), account.getUser().getId(), AccountEntryDTO.convertToDTOList(account.getAccountEntries()));
    }

    public static List<AccountDTO> convertToDTOList(List<Account> accounts){
        List<AccountDTO> accountsToReturn = new ArrayList<>();

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

    public List<AccountEntryDTO> getAccountEntries() {
        return accountEntries;
    }

    public void setAccountEntries(List<AccountEntryDTO> accountEntries) {
        this.accountEntries = accountEntries;
    }
}
