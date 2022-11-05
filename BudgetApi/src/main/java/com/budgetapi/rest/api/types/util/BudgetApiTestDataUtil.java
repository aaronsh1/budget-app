package com.budgetapi.rest.api.types.util;

import com.budgetapi.rest.api.types.BudgetApiUser;
import org.apache.catalina.User;

import java.util.ArrayList;
import java.util.List;

public class BudgetApiTestDataUtil {

    public static BudgetApiUser generateTestUser(int UserId){
        BudgetApiUser userToReturn = new BudgetApiUser();

        double randNum = Math.random();

        userToReturn.setUserId(UserId);
        userToReturn.setUsername("Tester"+randNum);
        userToReturn.setEmailAddress("test@user"+randNum);
        userToReturn.setFirstName("Name"+randNum);
        userToReturn.setLastName("Name"+randNum);

        return userToReturn;
    }

    public static List<BudgetApiUser> generateAllUsers(int num){
        List<BudgetApiUser> usersToReturn = new ArrayList<BudgetApiUser>();
        double userId = Math.random();
        for(int i = 0; i < num; i++){
            BudgetApiUser userToReturn = generateTestUser((int)userId);
        }
        return usersToReturn;
    }
}
