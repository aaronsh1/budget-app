package com.budget.api.controller;

import com.budget.api.DTO.BudgetDTO;
import com.budget.api.DTO.BudgetEntryDTO;
import com.budget.api.exception.ResourceNotFoundException;
import com.budget.api.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Component
@Path("/v1/budget")
public class BudgetController {
    
    @Autowired
    private BudgetService budgetService;

    @GET
    @Produces("application/json")
    @Path("/{budget-id}")
    public Response getBudget(@PathParam("budget-id") int budgetId){
        Response resp = null;
        try{
            BudgetDTO budgetInfo = budgetService.getBudget(budgetId);
            resp = Response.ok().entity(budgetInfo).build();
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
    @Consumes("application/json")
    @Path("/add")
    public Response addBudget(BudgetDTO budgetDTOToAdd){
        Response resp = null;

        try{
            BudgetDTO infoToReturn = budgetService.addBudget(budgetDTOToAdd);
            resp = Response.ok().entity(infoToReturn).build();
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
            BudgetDTO infoReturned = budgetService.updateBudget(budgetId, budgetDTO);
            resp = Response.ok().entity(infoReturned).build();
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
            BudgetDTO infoToReturn = budgetService.replaceBudget(budgetId, budgetDTO);
            resp = Response.ok().entity(infoToReturn).build();
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
    @Path("/{budget-id}/transaction")
    @Transactional
    public Response addEntryToBudget(@PathParam("budget-id") int budgetId, BudgetEntryDTO budgetEntryDTO){
        Response resp = null;
        try{
            BudgetEntryDTO infoToReturn = budgetService.addEntryToBudget(budgetId, budgetEntryDTO);
            resp = Response.ok().entity(infoToReturn).build();
        }catch (ResourceNotFoundException e){
            resp = Response.status(Response.Status.NOT_FOUND).entity(e).build();
        }catch (Exception e){
            resp = Response.status(Response.Status.METHOD_NOT_ALLOWED).entity(e).build();
        }finally {
            return resp;
        }
    }

    @DELETE
    @Produces("application/json")
    @Path("/{budget-id}")
    public Response deleteBudget(@PathParam("budget-id") int budgetId){
        Response resp = null;
        try{
            budgetService.deleteBudget(budgetId);
            resp = Response.ok().entity("{\"Budget Successfully Deleted.\"}").build();
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












