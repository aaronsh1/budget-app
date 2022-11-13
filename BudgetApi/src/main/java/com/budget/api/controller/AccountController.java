package com.budget.api.controller;

import com.budget.api.DTO.AccountDTO;
import com.budget.api.DTO.AccountEntryDTO;
import com.budget.api.exception.ResourceNotFoundException;
import com.budget.api.model.Account;
import com.budget.api.model.AccountEntry;
import com.budget.api.model.User;
import com.budget.api.repository.AccountEntryRepository;
import com.budget.api.repository.AccountRepository;
import com.budget.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/v1/account")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountEntryRepository accountEntryRepository;

    @GET
    @Produces("application/json")
    @Path("/{account-id}")
    @Transactional
    public Response getAccountById(@PathParam("account-id") int accountId){
        Response resp = null;
        try{
            Account accountToReturn = accountRepository.findById(accountId).orElse(null);
            if(accountToReturn == null)
                throw new ResourceNotFoundException("Cannot find account with given account number");

            resp = Response.ok().entity(AccountDTO.convertToDTO(accountToReturn)).build();
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
            AccountEntry accountEntryToReturn = accountEntryRepository.findById(accountEntryId).orElse(null);
            if(accountEntryToReturn == null)
                throw new ResourceNotFoundException("Cannot find account with given account number");

            resp = Response.ok().entity(AccountEntryDTO.convertToDTO(accountEntryToReturn)).build();
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
            Account account = accountRepository.findById((accountId)).orElse(null);
            if(account == null)
                throw new ResourceNotFoundException("Cannot find account with given account number");

            List<AccountEntry> entries = account.getAccountEntries();

            List<AccountEntryDTO> accountEntries = AccountEntryDTO.convertToDTOList(entries);

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
    @Path("/add")
    public Response addAccount(AccountDTO accountDTO){
        Response resp = null;
        try{
            User userForAccount = userRepository.findById(accountDTO.getUser()).orElse(null);
            if(userForAccount == null)
                throw new ResourceNotFoundException("Cannot find user with give user id");

            Account accountToAdd = new Account(userForAccount);
            resp = Response.ok().entity(AccountDTO.convertToDTO(accountRepository.save(accountToAdd))).build();
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
            Account accountForEntry = accountRepository.findById(accountEntryDTO.getAccount()).orElse(null);
            if(accountForEntry == null)
                throw new ResourceNotFoundException("Account does not exist");

            AccountEntry accountEntryToAdd = new AccountEntry(accountEntryDTO.getAmount(), accountForEntry, accountEntryDTO.getType());

            resp = Response.ok().entity(AccountEntryDTO.convertToDTO(accountEntryRepository.save(accountEntryToAdd))).build();
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
        Account correctAccount;
        boolean isReset = false;

        try{
            AccountEntry accountEntryToUpdate = accountEntryRepository.findById(accountEntryId).orElse(null);
            if(accountEntryToUpdate == null)
                throw new ResourceNotFoundException("Transaction not found.");
            if(accountEntryDTO.getAccount() != null){
                correctAccount = accountRepository.findById(accountEntryDTO.getAccount()).orElse(null);
                accountEntryToUpdate.setAccount(correctAccount);
                isReset = true;
            }
            if(accountEntryDTO.getAmount() != null){
                accountEntryToUpdate.setAmount(accountEntryDTO.getAmount());
                isReset = true;
            }
            if(isReset){
                resp = Response.ok().entity(AccountEntryDTO.convertToDTO(accountEntryRepository.save(accountEntryToUpdate))).build();
            }
            else{
                resp = Response.ok().entity("Nothing to update the account with.").build();
            }
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
            accountRepository.deleteById(accountId);
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
            accountEntryRepository.deleteById(accountEntryId);
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
