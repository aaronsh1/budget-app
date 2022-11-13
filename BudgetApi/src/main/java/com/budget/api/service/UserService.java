package com.budget.api.service;

import com.budget.api.DTO.AccountDTO;
import com.budget.api.DTO.BudgetDTO;
import com.budget.api.DTO.UserDTO;
import com.budget.api.exception.EmptyResourceException;
import com.budget.api.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Set;

public interface UserService {

    List<UserDTO> getAllUsers();

    UserDTO getUserById(int userId) throws ResourceNotFoundException;

    Set<BudgetDTO> getUserBudgets(int userId) throws ResourceNotFoundException;

    Set<AccountDTO> getUserAccounts(int userId) throws ResourceNotFoundException;

    UserDTO addUser(UserDTO userDTO) throws EmptyResourceException;

    UserDTO replaceUser(int userId, UserDTO userDTO) throws ResourceNotFoundException, EmptyResourceException;

    UserDTO updateUser(int userId, UserDTO userDTO) throws ResourceNotFoundException;

    void deleteUser(int userId);

    boolean isUserEmpty(UserDTO userDTO);
}
