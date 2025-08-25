package com.example.apigateway.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    @Lazy(false)
    public List<GroupedOpenApi> apis(RouteDefinitionLocator locator) {
        List<GroupedOpenApi> groups = new ArrayList<>();
        
        // User Service API Group
        groups.add(GroupedOpenApi.builder()
                .group("user-service")
                .displayName("User Service API")
                .pathsToMatch("/api/users/**")
                .build());
        
        // Policy Service API Group
        groups.add(GroupedOpenApi.builder()
                .group("policy-service")
                .displayName("Policy Service API")
                .pathsToMatch("/api/policies/**")
                .build());
        
        return groups;
    }
}
