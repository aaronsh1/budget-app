package com.budget.api.DTO;

import com.budget.api.model.User;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;


public class UserDTO {

//    region properties
    private String userName;

    private String firstName;

    private String lastName;

    private String emailAddress;

//    endregion

//    region getters&setters
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

//    endregion

    public static UserDTO convertToDTO(User user){
        UserDTO userDTOtoReturn = new UserDTO();
        userDTOtoReturn.emailAddress = user.getEmailAddress();
        userDTOtoReturn.userName = user.getUserName();
        userDTOtoReturn.firstName = user.getFirstName();
        userDTOtoReturn.lastName = user.getLastName();

        return userDTOtoReturn;
    }

    public static List<UserDTO> usersToDTO(List<User> users){
        List<UserDTO> userDTOsToReturn = new ArrayList<>();
        for(User user : users){
            userDTOsToReturn.add(convertToDTO(user));
        }

        return userDTOsToReturn;
    }

    @Override
    public String toString(){
        String userString = "";
        userString += "Username: " + this.userName;
        userString += "First name: " + this.firstName;
        userString += "Last name: " + this.lastName;
        userString += "Email: " + this.emailAddress;

        return userString;
    }
}
