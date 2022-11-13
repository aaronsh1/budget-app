package com.budget.api.controller;

import com.budget.api.DTO.AccountDTO;
import com.budget.api.DTO.AccountEntryDTO;
import com.budget.api.exception.ResourceNotFoundException;
import com.budget.api.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Set;

@Controller
@Path("/v1/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GET
    @Produces("application/json")
    @Path("/{account-id}")
    public Response getAccountById(@PathParam("account-id") int accountId){
        Response resp = null;
        try{
            AccountDTO accountToReturn = accountService.getAccountById(accountId);
            resp = Response.ok().entity(accountToReturn).build();
        }catch (ResourceNotFoundException e){
            resp = Response.status(Response.Status.NOT_FOUND).entity(e).build();
        }catch (Exception e){
            resp = Response.status(Response.Status.METHOD_NOT_ALLOWED).entity(e).build();
        }
        finally {
            return resp;
        }
    }

    @GET
    @Produces("application/json")
    @Path("/{account-id}/transactions/{account-entry-id}")
    public Response getTransaction(@PathParam("account-id") int accountId, @PathParam("account-entry-id") int accountEntryId){
        Response resp = null;
        try{
            AccountEntryDTO infoToReturn = accountService.getTransaction(accountId, accountEntryId);
            resp = Response.ok().entity(infoToReturn).build();
        }catch (ResourceNotFoundException e){
            resp = Response.status(Response.Status.NOT_FOUND).entity(e).build();
        }catch (Exception e){
            resp = Response.status(Response.Status.METHOD_NOT_ALLOWED).entity(e).build();
        }
        finally {
            return resp;
        }
    }

    @GET
    @Produces("application/json")
    @Path("/{account-id}/transactions")
    @Transactional
    public Response getAccountTransactions(@PathParam("account-id") int accountId){
        Response resp = null;
        try{
            Set<AccountEntryDTO> accountEntries = accountService.getAccountTransactions(accountId);
            resp = Response.ok().entity(accountEntries).build();
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
    public Response addAccount(AccountDTO accountDTO){
        Response resp = null;
        try{
            AccountDTO accountToReturn = accountService.addAccount(accountDTO);
            resp = Response.ok().entity(accountToReturn).build();
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
    @Path("/{account-id}/transactions")
    public Response addAccountTransaction(@PathParam("account-id") int accountId, AccountEntryDTO accountEntryDTO){
        Response resp = null;
        try{
            AccountEntryDTO transactionReturn = accountService.addAccountTransaction(accountId, accountEntryDTO);
            resp = Response.ok().entity(transactionReturn).build();
        }catch (ResourceNotFoundException e){
            resp = Response.status(Response.Status.NOT_FOUND).entity(e).build();
        }catch (Exception e){
            resp = Response.status(Response.Status.METHOD_NOT_ALLOWED).entity(e).build();
        }
        finally {
            return resp;
        }
    }

    @PATCH
    @Produces("application/json")
    @Path("/{account-id}/transactions/{account-entry-id}")
    public Response updateTransaction(@PathParam("account-entry-id") int accountEntryId, AccountEntryDTO accountEntryDTO){
        Response resp = null;
        try{
            AccountEntryDTO infoToReturn = accountService.updateTransaction(accountEntryId, accountEntryDTO);
            resp = Response.ok().entity(infoToReturn).build();
        }catch (ResourceNotFoundException e){
            resp = Response.status(Response.Status.NOT_FOUND).entity(e).build();
        }catch (Exception e){
            resp = Response.status(Response.Status.METHOD_NOT_ALLOWED).entity(e).build();
        }
        finally {
            return resp;
        }
    }

    @DELETE
    @Produces("application/json")
    @Path("/{account-id}")
    public Response deleteAccount(@PathParam("account-id") int accountId){
        Response resp = null;
        try{
            accountService.deleteAccount(accountId);
            resp = Response.ok().entity("{\"Account Successfully Deleted.\"}").build();
        }catch (EmptyResultDataAccessException e){
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
    @Path("/{account-id}/transactions/{account-entry-id}")
    public Response deleteTransaction(@PathParam("account-entry-id") int accountEntryId){
        Response resp = null;
        try{
            accountService.deleteTransaction(accountEntryId);
            resp = Response.ok().entity("{\"Transaction Successfully Deleted.\"}").build();
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
