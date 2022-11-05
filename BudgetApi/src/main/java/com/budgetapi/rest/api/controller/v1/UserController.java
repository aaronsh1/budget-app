package com.budgetapi.rest.api.controller.v1;

import com.budgetapi.rest.api.types.BudgetApiUser;
import com.budgetapi.rest.api.types.util.BudgetApiTestDataUtil;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

import io.swagger.annotations.Api;

@Component
@Path("/user")
@Produces("application/json")
@Api(value = "UserController Resource", produces = "application/json")
public class UserController {

    BudgetApiTestDataUtil testDataUtil = new BudgetApiTestDataUtil();

    @GET
    @Path("/{user-id}")
    public Response getUserInformation(@PathParam("user-id") int userId){
        BudgetApiUser userToReturn = testDataUtil.generateTestUser(userId);
        return Response.ok().entity(userToReturn).build();
    }

    @GET
    @Path("/all/{amount}")
    public Response getAllUsers(@PathParam("amount") int amountToReturn){
        List<BudgetApiUser> usersToReturn = testDataUtil.generateAllUsers(amountToReturn);
        return Response.ok().entity(usersToReturn).build();
    }

    @PUT
    @Path("/{user-id}")
    public Response replaceUser(@PathParam("user-id") int userId, BudgetApiUser user){
        BudgetApiUser replacedUser = testDataUtil.generateTestUser(userId);
        return Response.ok().entity(replacedUser).build();
    }

    @PATCH
    @Path("/{user-id}")
    public Response updateUser(@PathParam("user-id") int userId, BudgetApiUser user){
        BudgetApiUser userToUpdate = testDataUtil.generateTestUser(userId);
        if(!user.getUsername().equals(""))
            userToUpdate.setUsername(user.getUsername());
        if(!user.getEmailAddress().equals(""))
            userToUpdate.setEmailAddress(user.getEmailAddress());
        if(!user.getFirstName().equals(""))
            userToUpdate.setFirstName(user.getFirstName());
        if(!user.getLastName().equals(""))
            userToUpdate.setLastName(user.getLastName());

        return Response.ok().entity(userToUpdate).build();
    }

    @DELETE
    @Path("/{user-id}")
    public Response deleteUser(@PathParam("user-id") int userId){
        return Response.ok().entity("{\"User successfully deleted.\"}").build();
    }
}
