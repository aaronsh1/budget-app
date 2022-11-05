package com.budgetapi.config;

import com.budgetapi.rest.api.controller.v1.UserController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;

@Component
@ApplicationPath("/budgetapi")
public class JerseyConfig extends ResourceConfig {
    @PostConstruct
    public void init(){
        register(UserController.class);
    }

}
