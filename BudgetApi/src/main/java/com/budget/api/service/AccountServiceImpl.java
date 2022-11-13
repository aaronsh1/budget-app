package com.budget.api.service;

import com.budget.api.DTO.AccountDTO;
import com.budget.api.DTO.AccountEntryDTO;
import com.budget.api.exception.EmptyResourceException;
import com.budget.api.exception.ResourceNotFoundException;
import com.budget.api.model.Account;
import com.budget.api.model.AccountEntry;
import com.budget.api.model.User;
import com.budget.api.repository.AccountEntryRepository;
import com.budget.api.repository.AccountRepository;
import com.budget.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountEntryRepository accountEntryRepository;

    @Override
    @Transactional
    public AccountDTO getAccountById(int accountId) throws ResourceNotFoundException {
        Account accountToReturn = accountRepository.findById(accountId).orElse(null);
        if(accountToReturn == null)
            throw new ResourceNotFoundException("Cannot find account with given account number");

        AccountDTO infoReturned = AccountDTO.convertToDTO(accountToReturn);
        return infoReturned;
    }

    @Override
    public AccountEntryDTO getTransaction(int accountId, int accountEntryId) throws ResourceNotFoundException {
        AccountEntry accountEntryToReturn = accountEntryRepository.findById(accountEntryId).orElse(null);
        if(accountEntryToReturn == null)
            throw new ResourceNotFoundException("Cannot find account with given account number");

        AccountEntryDTO infoReturn = AccountEntryDTO.convertToDTO(accountEntryToReturn);
        return infoReturn;
    }

    @Override
    public Set<AccountEntryDTO> getAccountTransactions(int accountId) throws ResourceNotFoundException {
        Account account = accountRepository.findById((accountId)).orElse(null);
        if(account == null)
            throw new ResourceNotFoundException("Cannot find account with given account number");

        Set<AccountEntry> entries = account.getAccountEntries();

        Set<AccountEntryDTO> accountEntries = AccountEntryDTO.convertToDTOList(entries);

        return accountEntries;
    }

    @Override
    public AccountDTO addAccount(AccountDTO accountDTO) throws ResourceNotFoundException {
        User userForAccount = userRepository.findById(accountDTO.getUser()).orElse(null);
        if(userForAccount == null)
            throw new ResourceNotFoundException("Cannot find user with give user id");

        Account accountToAdd = new Account(userForAccount);
        return AccountDTO.convertToDTO(accountToAdd);
    }

    @Override
    public AccountEntryDTO addAccountTransaction(int accountId, AccountEntryDTO accountEntryDTO) throws ResourceNotFoundException {
        Account accountForEntry = accountRepository.findById(accountEntryDTO.getAccount()).orElse(null);
        if(accountForEntry == null)
            throw new ResourceNotFoundException("Account does not exist");

        AccountEntry accountEntryToAdd = new AccountEntry(accountEntryDTO.getAmount(), accountForEntry, accountEntryDTO.getType());
        AccountEntryDTO transactionAdded = AccountEntryDTO.convertToDTO(accountEntryRepository.save(accountEntryToAdd));

        return transactionAdded;
    }

    @Override
    public AccountEntryDTO updateTransaction(int accountEntryId, AccountEntryDTO accountEntryDTO) throws ResourceNotFoundException, EmptyResourceException {
        boolean isReset = false;
        Account correctAccount;
        AccountEntry accountEntryToUpdate = accountEntryRepository.findById(accountEntryId).orElse(null);
        if(accountEntryToUpdate == null)
            throw new ResourceNotFoundException("Transaction not found.");
        if(accountEntryDTO.getAccount() != null){
            correctAccount = accountRepository.findById(accountEntryDTO.getAccount()).orElse(null);
            accountEntryToUpdate.setAccount(correctAccount);
            isReset = true;
        }
        if(accountEntryDTO.getAmount() != null){
            accountEntryToUpdate.setAmount(accountEntryDTO.getAmount());
            isReset = true;
        }
        if(!isReset){
            throw new EmptyResourceException("Nothing to update the account with.");
        }
        AccountEntryDTO infoToReturn = AccountEntryDTO.convertToDTO(accountEntryToUpdate);
        return infoToReturn;
    }

    @Override
    public void deleteAccount(int accountId) {
        accountRepository.deleteById(accountId);
    }

    @Override
    public void deleteTransaction(int accountEntryId) {
        accountEntryRepository.deleteById(accountEntryId);
    }
}
