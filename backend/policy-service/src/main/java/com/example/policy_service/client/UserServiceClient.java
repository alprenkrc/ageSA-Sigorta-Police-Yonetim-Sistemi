package com.example.policy_service.client;

import com.example.policy_service.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", path = "/users")
public interface UserServiceClient {
    
    @GetMapping("/{id}")
    UserDTO getUserById(@PathVariable Long id);
}
