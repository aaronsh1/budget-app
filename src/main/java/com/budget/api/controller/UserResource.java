package com.budget.api.controller;

import com.budget.api.model.User;
import com.budget.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Component
@Path("/v1/user")
public class UserResource {

    @Autowired
    UserRepository userRepository;

    @GET
    @Produces("application/json")
    @Path("/users")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
