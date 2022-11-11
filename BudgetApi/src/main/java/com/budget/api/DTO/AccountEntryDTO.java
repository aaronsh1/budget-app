package com.budget.api.DTO;

import com.budget.api.model.Account;
import com.budget.api.model.AccountEntry;

import java.util.ArrayList;
import java.util.List;


public class AccountEntryDTO {

    private Integer amount;

    private String type;

    private Integer account;

    public AccountEntryDTO(Integer amount, String type) {
        this.amount = amount;
        this.type = type;
    }

    public AccountEntryDTO(){}

    public static AccountEntryDTO convertToDTO(AccountEntry accountEntry){
        return new AccountEntryDTO(accountEntry.getAmount(), accountEntry.getType());
    }

    public static List<AccountEntryDTO> convertToDTOList(List<AccountEntry> accountEntries){
        List<AccountEntryDTO> accountEntriesToReturn = new ArrayList<>();

        for(AccountEntry accountEntry : accountEntries){
            accountEntriesToReturn.add(convertToDTO(accountEntry));
        }

        return accountEntriesToReturn;
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
