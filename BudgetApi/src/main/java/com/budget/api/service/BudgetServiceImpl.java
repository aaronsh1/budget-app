package com.budget.api.service;

import com.budget.api.DTO.BudgetDTO;
import com.budget.api.DTO.BudgetEntryDTO;
import com.budget.api.exception.EmptyResourceException;
import com.budget.api.exception.ResourceNotFoundException;
import com.budget.api.model.Budget;
import com.budget.api.model.BudgetEntry;
import com.budget.api.model.User;
import com.budget.api.repository.BudgetEntryRepository;
import com.budget.api.repository.BudgetRepository;
import com.budget.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BudgetServiceImpl implements BudgetService{

    @Autowired
    private BudgetRepository budgetRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BudgetEntryRepository budgetEntryRepository;

    @Override
    @Transactional
    public BudgetDTO getBudget(int budgetId) throws ResourceNotFoundException {
        Budget budgetToReturn = budgetRepository.findById(budgetId).orElse(null);
        if(budgetToReturn == null)
            throw new ResourceNotFoundException("Cannot find the budget with the given ID number");
        BudgetDTO budgetInfo = BudgetDTO.convertToDTO(budgetToReturn);

        return budgetInfo;
    }

    @Override
    public BudgetDTO addBudget(BudgetDTO budgetDTOToAdd) {
        Set<User> usersForBudget = new HashSet<>(userRepository.findAllById(budgetDTOToAdd.getUsers()));
        Budget budgetToAdd = BudgetDTO.toBudget(budgetDTOToAdd, usersForBudget);
        BudgetDTO infoToReturn = BudgetDTO.convertToDTO(budgetRepository.save(budgetToAdd));
        return  infoToReturn;
    }

    @Override
    @Transactional
    public BudgetDTO updateBudget(int budgetId, BudgetDTO budgetDTO) throws ResourceNotFoundException {
        Budget budgetToUpdate = budgetRepository.findById(budgetId).orElse(null);
        if(budgetToUpdate == null){
            throw new ResourceNotFoundException("Budget not found for the given id.");
        }

        if(budgetDTO.getGoal() != null && budgetDTO.getGoal() !=0)
            budgetToUpdate.setGoal(budgetDTO.getGoal());

        if(budgetDTO.getUsers() != null && budgetDTO.getUsers().size() != 0){
            List<User> usersForBudget = userRepository.findAllById(budgetDTO.getUsers());
            budgetToUpdate.getUsers().addAll(usersForBudget);
        }

        if(budgetDTO.getSaved() != null && budgetDTO.getSaved() != 0)
            budgetToUpdate.setSaved(budgetDTO.getSaved());

        Budget infoReturned = budgetRepository.save(budgetToUpdate);

        return BudgetDTO.convertToDTO(infoReturned);
    }

    @Override
    public BudgetDTO replaceBudget(int budgetId, BudgetDTO budgetDTO) throws ResourceNotFoundException, EmptyResourceException {
        if(isBudgetEmpty(budgetDTO))
            throw new EmptyResourceException("Empty budget passed in.");
        if(!budgetRepository.existsById(budgetId))
            throw new ResourceNotFoundException("Cannot find budget with given ID");

        Budget budgetForReplace = new Budget(budgetDTO.getGoal(), budgetDTO.getSaved());
        budgetForReplace.setId(budgetId);

        List<User> usersForBudget = userRepository.findAllById(budgetDTO.getUsers());
        budgetForReplace.setUsers(new HashSet<>(usersForBudget));

        Budget replacedBudget = budgetRepository.save(budgetForReplace);

        BudgetDTO infoToReturn = BudgetDTO.convertToDTO(replacedBudget);

        return infoToReturn;
    }

    @Override
    public BudgetEntryDTO addEntryToBudget(int budgetId, BudgetEntryDTO budgetEntryDTO) throws ResourceNotFoundException {
        User userForEntry = userRepository.findById(budgetEntryDTO.getUser()).orElse(null);
        if(userForEntry == null)
            throw new ResourceNotFoundException("Cannot find user with the given id.");
        Budget budgetForEntry = budgetRepository.findById(budgetId).orElse(null);
        if(budgetForEntry == null)
            throw new ResourceNotFoundException("Cannot find budget with the given id.");

        int currentSaved = budgetForEntry.getSaved();
        int adjustAmount = budgetEntryDTO.getAmount();
        budgetForEntry.setSaved(currentSaved + adjustAmount);

        BudgetEntry budgetEntry = budgetEntryRepository.save(new BudgetEntry(userForEntry, budgetForEntry, adjustAmount));
        BudgetEntryDTO infoToReturn = BudgetEntryDTO.convertToDTO(budgetEntry);

        return infoToReturn;
    }

    @Override
    public void deleteBudget(int budgetId) {
        budgetRepository.deleteById(budgetId);
    }

    @Override
    public boolean isBudgetEmpty(BudgetDTO budgetDTO){
        if(budgetDTO.getGoal() == null || budgetDTO.getGoal() == 0)
            return true;
        else if(budgetDTO.getSaved() == null || budgetDTO.getSaved() == 0)
            return true;
        else if(budgetDTO.getUsers() == null || budgetDTO.getUsers().size() == 0)
            return true;
        else
            return false;
    }
}
