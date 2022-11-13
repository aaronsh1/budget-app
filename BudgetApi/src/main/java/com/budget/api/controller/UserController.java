package com.budget.api.controller;

import com.budget.api.DTO.AccountDTO;
import com.budget.api.DTO.BudgetDTO;
import com.budget.api.DTO.UserDTO;
import com.budget.api.exception.ResourceNotFoundException;
import com.budget.api.model.User;
import com.budget.api.repository.UserRepository;
import com.budget.api.service.UserService;
import com.budget.api.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;

@Controller
@Path("/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GET
    @Produces("application/json")
    @Path("/all")
    public Response getAllUsers(){
        Response resp = null;
        try{
            List<UserDTO> usersToReturn = userService.getAllUsers();
            resp = Response.ok().entity(usersToReturn).build();
        } catch (Exception e){
            resp = Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
        }
        finally {
            return resp;
        }
    }

    @GET
    @Produces("application/json")
    @Path("/{user-id}")
    public Response getUserById(@PathParam("user-id") int userId) throws ResourceNotFoundException {
        Response resp = null;
        try{
            UserDTO userToReturn = userService.getUserById(userId);
            resp = Response.ok().entity(userToReturn).build();
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
    @Path("/{user-id}/budgets")
    @Transactional
    public Response getUserBudgets(@PathParam("user-id") int userId) throws ResourceNotFoundException{
        Response resp = null;
        try{
            Set<BudgetDTO> budgetsToReturn = userService.getUserBudgets(userId);
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
            Set<AccountDTO> accounts = userService.getUserAccounts(userId);
            resp = Response.ok().entity(accounts).build();
        }
        catch (ResourceNotFoundException e){
            resp = Response.status(Response.Status.NOT_FOUND).entity("{\"Exception\":" + "{\""+e+"\"}" + "}").build();
        }
        catch (Exception e){
            resp = Response.status(Response.Status.METHOD_NOT_ALLOWED).entity("{\"Exception\":" + "{\""+e+"\"}" + "}").build();
        }
        finally {
            return resp;
        }
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response addUser(UserDTO userDTO){
        Response resp = null;

        try{
            UserDTO addedUser = userService.addUser(userDTO);
            resp = Response.ok().entity(addedUser).build();
        }catch (Exception e){
            resp = Response.status(Response.Status.NOT_FOUND).build();
        }finally{
            return resp;
        }
    }

    @PUT
    @Produces("application/json")
    @Path("/{user-id}")
    public Response replaceUser(@PathParam("user-id") int userId, UserDTO userDTO) throws ResourceNotFoundException {
        Response resp = null;
        try{
            UserDTO returnObj = userService.replaceUser(userId, userDTO);
            resp = Response.ok().entity(returnObj).build();
        }
        catch (ResourceNotFoundException e){
            resp = Response.status(Response.Status.NOT_FOUND).entity("{\"Exception\":" + "{\""+e+"\"}" + "}").build();
        }catch(Exception e){
            resp = Response.status(Response.Status.NOT_FOUND).entity("{\"Exception\":" + "{\""+e+"\"}" + "}").build();
        }
        finally {
            return resp;
        }
    }

    @PATCH
    @Produces("application/json")
    @Path("/{user-id}")
    public Response updateUser(@PathParam("user-id") int userId, UserDTO userDTO) throws ResourceNotFoundException {
        Response resp = null;
        try{
            UserDTO updatedUser = userService.updateUser(userId, userDTO);
            resp = Response.ok().entity(updatedUser).build();
        }
        catch (ResourceNotFoundException e){
            resp = Response.status(Response.Status.NOT_FOUND).entity("{\"Exception\":" + "{\""+e+"\"}" + "}").build();
        }catch(Exception e){
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
            userService.deleteUser(userId);
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
