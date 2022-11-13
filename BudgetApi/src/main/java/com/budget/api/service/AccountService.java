package com.budget.api.service;

import com.budget.api.DTO.AccountDTO;
import com.budget.api.DTO.AccountEntryDTO;
import com.budget.api.exception.EmptyResourceException;
import com.budget.api.exception.ResourceNotFoundException;

import java.util.Set;

public interface AccountService {

    AccountDTO getAccountById(int accountId) throws ResourceNotFoundException;

    AccountEntryDTO getTransaction(int accountId, int accountEntryId) throws ResourceNotFoundException;

    Set<AccountEntryDTO> getAccountTransactions(int accountId) throws ResourceNotFoundException;

    AccountDTO addAccount(AccountDTO accountDTO) throws ResourceNotFoundException;

    AccountEntryDTO addAccountTransaction(int accountId, AccountEntryDTO accountEntryDTO) throws ResourceNotFoundException;

    AccountEntryDTO updateTransaction(int accountEntryId, AccountEntryDTO accountEntryDTO) throws ResourceNotFoundException, EmptyResourceException;

    void deleteAccount(int accountId);

    void deleteTransaction(int accountEntryId);
}
