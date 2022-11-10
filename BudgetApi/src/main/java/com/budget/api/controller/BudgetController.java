package com.budget.api.controller;

import com.budget.api.DTO.BudgetDTO;
import com.budget.api.DTO.BudgetEntryDTO;
import com.budget.api.exception.ResourceNotFoundException;
import com.budget.api.model.Budget;
import com.budget.api.model.BudgetEntry;
import com.budget.api.model.User;
import com.budget.api.repository.BudgetEntryRepository;
import com.budget.api.repository.BudgetRepository;
import com.budget.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Component
@Path("/v1/budget")
public class BudgetController {
    
    @Autowired
    private BudgetRepository budgetRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BudgetEntryRepository budgetEntryRepository;

    @GET
    @Produces("application/json")
    @Path("/{budget-id}")
    public Response getBudget(@PathParam("budget-id") int budgetId){
        Response resp = null;
        try{
            Budget budgetToReturn = budgetRepository.findById(budgetId).orElse(null);
            if(budgetToReturn == null)
                throw new ResourceNotFoundException("Cannot find the budget with the given ID number");

            resp = Response.ok().entity(budgetToReturn).build();
        }catch (ResourceNotFoundException e){
            resp = Response.status(Response.Status.NOT_FOUND).entity(e).build();
        }catch (Exception e){
            resp = Response.status(Response.Status.METHOD_NOT_ALLOWED).entity(e).build();
        }
        finally {
            return resp;
        }
    }
    
    @POST
    @Produces("application/json")
    @Path("/add")
    public Response addBudget(BudgetDTO budgetDTOToAdd){
        Response resp = null;

        try{
            Budget budgetToAdd = BudgetDTO.toBudget(budgetDTOToAdd);
            resp = Response.ok().entity(budgetRepository.save(budgetToAdd)).build();
        }catch (Exception e){
            resp = Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
        }finally {
            return resp;
        }
    }

    @PATCH
    @Produces("application/json")
    @Path("/{budget-id}")
    public Response updateBudget(@PathParam("budget-id") int budgetId, BudgetDTO budgetDTO){
        Response resp = null;

        try{
            Budget budgetToUpdate = budgetRepository.findById(budgetId).orElse(null);
            if(budgetToUpdate == null){
                throw new ResourceNotFoundException("Budget not found for the given id.");
            }

            if(!budgetDTO.getGoal().equals(""))
                budgetToUpdate.setGoal(budgetDTO.getGoal());

            if(!budgetDTO.getSaved().equals(""))
                budgetToUpdate.setSaved(budgetDTO.getSaved());

            resp = Response.ok().entity(budgetToUpdate).build();
        }catch (ResourceNotFoundException e){
            resp = Response.status(Response.Status.NOT_FOUND).entity(e).build();
        }catch (Exception e){
            resp = Response.status(Response.Status.METHOD_NOT_ALLOWED).entity(e).build();
        }finally {
            return resp;
        }
    }

    @PUT
    @Produces("application/json")
    @Path("/{budget-id}")
    public Response replaceBudget(@PathParam("budget-id") int budgetId, BudgetDTO budgetDTO){
        Response resp = null;

        try{
            if(budgetRepository.existsById(budgetId))
                throw new ResourceNotFoundException("Cannot find budget with given ID");
            Budget budgetForReplace = new Budget(budgetDTO.getGoal(), budgetDTO.getSaved());
            budgetForReplace.setId(budgetId);

            Budget replacedBudget = budgetRepository.save(budgetForReplace);

            resp = Response.ok().entity(replacedBudget).build();
        }catch (ResourceNotFoundException e){
            resp = Response.status(Response.Status.NOT_FOUND).entity(e).build();
        }catch (Exception e){
            resp = Response.status(Response.Status.METHOD_NOT_ALLOWED).entity(e).build();
        }finally {
            return resp;
        }
    }

    @POST
    @Produces("application/json")
    @Path("/entry")
    public Response addEntryToBudget(BudgetEntryDTO budgetEntryDTO){
        Response resp = null;
        try{
            User userForEntry = userRepository.findById(budgetEntryDTO.getUser()).orElse(null);
            if(userForEntry == null)
                throw new ResourceNotFoundException("Cannot find user with the given id.");
            Budget budgetForEntry = budgetRepository.findById(budgetEntryDTO.getBudget()).orElse(null);
            if(budgetForEntry == null)
                throw new ResourceNotFoundException("Cannot find budget with the given id.");

            int currentSaved = budgetForEntry.getSaved();
            int adjustAmount = budgetEntryDTO.getAmount();
            budgetForEntry.setSaved(currentSaved + adjustAmount);

            BudgetEntry budgetEntry = new BudgetEntry(userForEntry, budgetForEntry, adjustAmount);
            budgetEntryRepository.save(budgetEntry);

            resp = Response.ok().entity(budgetRepository.save(budgetForEntry)).build();
        }catch (ResourceNotFoundException e){
            resp = Response.status(Response.Status.NOT_FOUND).entity(e).build();
        }catch (Exception e){
            resp = Response.status(Response.Status.METHOD_NOT_ALLOWED).entity(e).build();
        }finally {
            return resp;
        }
    }
}












