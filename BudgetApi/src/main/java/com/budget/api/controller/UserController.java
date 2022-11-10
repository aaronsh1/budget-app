package com.budget.api.controller;

import com.budget.api.DTO.AccountDTO;
import com.budget.api.DTO.BudgetDTO;
import com.budget.api.DTO.UserDTO;
import com.budget.api.exception.ResourceNotFoundException;
import com.budget.api.model.User;
import com.budget.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/v1/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GET
    @Produces("application/json")
    @Path("/all")
    public Response getAllUsers(){
        List<User> users = userRepository.findAll();
        List<UserDTO> usersToReturn = UserDTO.usersToDTO(users);

        return Response.ok().entity(usersToReturn).build();
    }

    @GET
    @Produces("application/json")
    @Path("/{user-id}")
    public Response getUserById(@PathParam("user-id") int userId) throws ResourceNotFoundException {
        Response resp = null;
        try{
            User userObj = userRepository.findById(userId).orElse(null);
            if(userObj == null)
                throw new ResourceNotFoundException("The user with the specified user id could not be found");

            UserDTO userToReturn = UserDTO.convertToDTO(userObj);
            resp = Response.ok().entity(userToReturn).build();
        }
        catch (ResourceNotFoundException e){
            resp = Response.status(Response.Status.NOT_FOUND).entity("{\"Exception\":" + "{\""+e+"\"}" + "}").build();
        }
        finally {
            return resp;
        }

    }

    @GET
    @Produces("application/json")
    @Path("/{user-id}/budgets")
    @Transactional
    public Response getUserBudgets(@PathParam("user-id") int userId) throws ResourceNotFoundException{
        Response resp = null;
        try{
            User user = userRepository.findById(userId).orElse(null);
            if(user == null)
                throw new ResourceNotFoundException("Cannot find user with the given user id.");

            List<BudgetDTO> budgetsToReturn = BudgetDTO.convertToDTOList(user.getBudgets());

            resp = Response.ok().entity(budgetsToReturn).build();
        }
        catch (ResourceNotFoundException e){
            resp = Response.status(Response.Status.NOT_FOUND).entity("{\"Exception\":" + "{\""+e+"\"}" + "}").build();
        }
        catch(Exception e){
            resp = Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("{\"Exception\":" + "{\""+e+"\"}" + "}").build();
        }
        finally {
            return resp;
        }
    }

    @GET
    @Produces("application/json")
    @Path("/{user-id}/accounts")
    @Transactional
    public Response getUserAccounts(@PathParam("user-id") int userId) throws ResourceNotFoundException{
        Response resp = null;

        try{
            User user = userRepository.findById(userId).orElse(null);
            if(user == null)
                throw new ResourceNotFoundException("User not found for given user ID");

            user.getAccounts().size();

            List<AccountDTO> accounts = AccountDTO.convertToDTOList(user.getAccounts());

            resp = Response.ok().entity(accounts).build();
        }
        catch (ResourceNotFoundException e){
            resp = Response.status(Response.Status.NOT_FOUND).entity("{\"Exception\":" + "{\""+e+"\"}" + "}").build();
        }
        finally {
            return resp;
        }
    }

    @PUT
    @Produces("application/json")
    @Path("/{user-id}")
    public Response replaceUser(@PathParam("user-id") int userId, User user) throws ResourceNotFoundException {
        Response resp = null;
        try{
            User oldUser = userRepository.findById(userId).orElse(null);
            if(oldUser == null)
                throw new ResourceNotFoundException("User not found with the given ID number");

            user.setId(oldUser.getId());

            UserDTO returnObj = UserDTO.convertToDTO(userRepository.save(user));
            resp = Response.ok().entity(returnObj).build();
        }
        catch (ResourceNotFoundException e){
            resp = Response.status(Response.Status.NOT_FOUND).entity("{\"Exception\":" + "{\""+e+"\"}" + "}").build();
        }
        finally {
            return resp;
        }

    }

    @PATCH
    @Produces("application/json")
    @Path("/{user-id}")
    public Response updateUser(@PathParam("user-id") int userId, User user) throws ResourceNotFoundException {
        Response resp = null;
        try{
            User userToUpdate = userRepository.findById(userId).orElse(null);
            if(userToUpdate == null)
                throw new ResourceNotFoundException("User not found with the given ID number");

            if(user.getUserName() != null && !user.getUserName().equals(""))
                userToUpdate.setUserName(user.getUserName());

            if(user.getFirstName() != null && !user.getFirstName().equals(""))
                userToUpdate.setFirstName(user.getFirstName());

            if(user.getLastName() != null && !user.getLastName().equals(""))
                userToUpdate.setLastName(user.getLastName());

            if(user.getEmailAddress() != null && !user.getEmailAddress().equals(""))
                userToUpdate.setEmailAddress(user.getEmailAddress());

            resp = Response.ok().entity(UserDTO.convertToDTO(userToUpdate)).build();
        }
        catch (ResourceNotFoundException e){
            resp = Response.status(Response.Status.NOT_FOUND).entity("{\"Exception\":" + "{\""+e+"\"}" + "}").build();
        }
        finally {
            return resp;
        }
    }

    @DELETE
    @Produces("application/json")
    @Path("/{user-id}")
    public Response deleteUser(@PathParam("user-id") int userId){
        Response resp = null;
        try{
            userRepository.deleteById(userId);
            resp =  Response.ok().entity("{\"User Successfully Deleted\"}").build();
        }catch (EmptyResultDataAccessException e){
            resp = Response.status(Response.Status.NOT_FOUND).entity("{\"Exception\":" + "{\""+e+"\"}" + "}").build();
        }catch(Exception e){
            resp = Response.status(Response.Status.NOT_FOUND).entity("{\"Exception\":" + "{\""+e+"\"}" + "}").build();
        }
        finally {
            return resp;
        }


    }
}
