package com.budget.api.controller;

import com.budget.api.DTO.AccountDTO;
import com.budget.api.DTO.AccountEntryDTO;
import com.budget.api.exception.EmptyResourceException;
import com.budget.api.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Set;

@RestController
@RequestMapping("/v1/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{account-id}")
    public AccountDTO getAccountById(@PathVariable("account-id") int accountId){
        return accountService.getAccountById(accountId);
    }

    @GetMapping("/{account-id}/transactions/{account-entry-id}")
    public AccountEntryDTO getTransaction(@PathVariable("account-id") int accountId, @PathVariable("account-entry-id") int accountEntryId){
        return accountService.getTransaction(accountId, accountEntryId);
    }

    @GetMapping("/{account-id}/transactions")
    @Transactional
    public Set<AccountEntryDTO> getAccountTransactions(@PathVariable("account-id") int accountId){
        return accountService.getAccountTransactions(accountId);
    }

    @PostMapping
    public AccountDTO addAccount(@RequestBody AccountDTO accountDTO){
        return accountService.addAccount(accountDTO);
    }

    @PostMapping("/{account-id}/transactions")
    public AccountEntryDTO addAccountTransaction(@PathVariable("account-id") int accountId, @RequestBody AccountEntryDTO accountEntryDTO){
        return accountService.addAccountTransaction(accountId, accountEntryDTO);
    }

    @PatchMapping("/{account-id}/transactions/{account-entry-id}")
    public AccountEntryDTO updateTransaction(@PathVariable("account-entry-id") int accountEntryId, AccountEntryDTO accountEntryDTO) throws EmptyResourceException {
        return accountService.updateTransaction(accountEntryId, accountEntryDTO);
    }

    @DeleteMapping("/{account-id}")
    public String deleteAccount(@PathVariable("account-id") int accountId){
        accountService.deleteAccount(accountId);
        return "{\"Account Successfully Deleted.\"}";
    }

    @DeleteMapping("/{account-id}/transactions/{account-entry-id}")
    public String deleteTransaction(@PathVariable("account-entry-id") int accountEntryId){
        accountService.deleteTransaction(accountEntryId);
        return "{\"Transaction Successfully Deleted.\"}";
    }
}
