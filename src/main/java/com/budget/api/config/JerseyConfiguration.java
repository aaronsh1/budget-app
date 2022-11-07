package com.budget.api.config;

import com.budget.api.controller.UserResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

@Component
@ApplicationPath("/budget")
public class JerseyConfiguration extends ResourceConfig {
    public JerseyConfiguration(){
        register(UserResource.class);
    }
}