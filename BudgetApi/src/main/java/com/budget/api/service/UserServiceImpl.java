package com.budget.api.service;

import com.budget.api.DTO.AccountDTO;
import com.budget.api.DTO.BudgetDTO;
import com.budget.api.DTO.UserDTO;
import com.budget.api.exception.EmptyResourceException;
import com.budget.api.exception.ResourceNotFoundException;
import com.budget.api.model.User;
import com.budget.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDTO> getAllUsers(){
        List<UserDTO> usersToReturn;
        List<User> users = userRepository.findAll();
        usersToReturn = UserDTO.usersToDTO(users);

        return usersToReturn;
    }

    @Override
    public UserDTO getUserById(int userId) throws ResourceNotFoundException {
        User userObj = userRepository.findById(userId).orElse(null);
        if(userObj == null)
            throw new ResourceNotFoundException("The user with the specified user id could not be found");

        UserDTO userToReturn = UserDTO.convertToDTO(userObj);
        return userToReturn;

    }

    @Override
    @Transactional
    public Set<BudgetDTO> getUserBudgets(int userId) throws ResourceNotFoundException{
        User user = userRepository.findById(userId).orElse(null);
        if(user == null)
            throw new ResourceNotFoundException("Cannot find user with the given user id.");

        Set<BudgetDTO> budgetsToReturn = BudgetDTO.convertToDTOList(user.getBudgets());

        return budgetsToReturn;
    }

    @Override
    @Transactional
    public Set<AccountDTO> getUserAccounts(int userId) throws ResourceNotFoundException{
        User user = userRepository.findById(userId).orElse(null);
        if(user == null)
            throw new ResourceNotFoundException("User not found for given user ID");

        Set<AccountDTO> accounts = AccountDTO.convertToDTOList(user.getAccounts());
        return accounts;
    }

    @Override
    public UserDTO addUser(UserDTO userDTO) throws EmptyResourceException {
        if(isUserEmpty(userDTO))
            throw new EmptyResourceException("THe user provided is missing values.");

        User userToAdd = new User(userDTO.getUserName(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmailAddress());
        User userAdded = userRepository.save(userToAdd);
        UserDTO userInfo =  UserDTO.convertToDTO(userAdded);
        return userInfo;
    }

    @Override
    public UserDTO replaceUser(int userId, UserDTO userDTO) throws ResourceNotFoundException, EmptyResourceException {

        User oldUser = userRepository.findById(userId).orElse(null);
        if(isUserEmpty(userDTO))
            throw new EmptyResourceException("THe user provided is missing values.");

        if(oldUser == null)
            throw new ResourceNotFoundException("User not found with the given ID number");
        User userForReplacement = new User(userDTO.getUserName(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmailAddress());

        userForReplacement.setId(oldUser.getId());
        UserDTO returnObj = UserDTO.convertToDTO(userRepository.save(userForReplacement));

        return returnObj;
    }

    @Override
    public UserDTO updateUser(int userId, UserDTO userDTO) throws ResourceNotFoundException {
        User userToUpdate = userRepository.findById(userId).orElse(null);
        if(userToUpdate == null)
            throw new ResourceNotFoundException("User not found with the given ID number");

        if(userDTO.getUserName() != null && !userDTO.getUserName().equals(""))
            userToUpdate.setUserName(userDTO.getUserName());

        if(userDTO.getFirstName() != null && !userDTO.getFirstName().equals(""))
            userToUpdate.setFirstName(userDTO.getFirstName());

        if(userDTO.getLastName() != null && !userDTO.getLastName().equals(""))
            userToUpdate.setLastName(userDTO.getLastName());

        if(userDTO.getEmailAddress() != null && !userDTO.getEmailAddress().equals(""))
            userToUpdate.setEmailAddress(userDTO.getEmailAddress());

        User updatedUser = userRepository.save(userToUpdate);
        return UserDTO.convertToDTO(updatedUser);
    }

    @Override
    public void deleteUser(int userId){
        userRepository.deleteById(userId);
    }

    @Override
    public boolean isUserEmpty(UserDTO userDTO){
        if(userDTO.getUserName() == null && userDTO.getUserName().equals(""))
            return true;

        else if(userDTO.getFirstName() == null && userDTO.getFirstName().equals(""))
            return true;

        else if(userDTO.getLastName() == null && userDTO.getLastName().equals(""))
            return true;

        else if(userDTO.getEmailAddress() == null && userDTO.getEmailAddress().equals(""))
            return true;
        else{
            return false;
        }
    }
}
