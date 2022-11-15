package com.budget.api.controller;

import com.budget.api.DTO.AccountDTO;
import com.budget.api.DTO.BudgetDTO;
import com.budget.api.DTO.UserDTO;
import com.budget.api.exception.EmptyResourceException;
import com.budget.api.exception.ResourceNotFoundException;
import com.budget.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{user-id}")
    public UserDTO getUserById(@PathVariable("user-id") int userId) throws ResourceNotFoundException {
        return userService.getUserById(userId);
    }

    @GetMapping("/{user-id}/budgets")
    @Transactional
    public Set<BudgetDTO> getUserBudgets(@PathVariable int userId) throws ResourceNotFoundException{
        return userService.getUserBudgets(userId);
    }

    @GetMapping("/{user-id}/accounts")
    @Transactional
    public Set<AccountDTO> getUserAccounts(@PathVariable int userId) throws ResourceNotFoundException{
        return userService.getUserAccounts(userId);
    }

    @PostMapping
    public UserDTO addUser(@RequestBody UserDTO userDTO) throws EmptyResourceException {
        return userService.addUser(userDTO);
    }

    @PutMapping("/{user-id}")
    public UserDTO replaceUser(@PathVariable int userId, @RequestBody UserDTO userDTO) throws ResourceNotFoundException, EmptyResourceException {
        return userService.replaceUser(userId, userDTO);
    }

    @PatchMapping("/{user-id}")
    public UserDTO updateUser(@PathVariable int userId, @RequestBody UserDTO userDTO) throws ResourceNotFoundException {
        return userService.updateUser(userId, userDTO);
    }

    @DeleteMapping("/{user-id}")
    public String deleteUser(@PathVariable int userId){
        userService.deleteUser(userId);
        return "{\"User Successfully Deleted\"}";
    }
}
