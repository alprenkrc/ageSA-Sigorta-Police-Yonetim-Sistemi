package com.example.policy_service.controller;

import com.example.policy_service.dto.CreatePolicyDTO;
import com.example.policy_service.dto.PolicyDTO;
import com.example.policy_service.entity.Policy;
import com.example.policy_service.service.PolicyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/policies")
@RequiredArgsConstructor
@Tag(name = "Policy Management", description = "Sigorta poliçesi yönetimi işlemleri")
public class PolicyController {
    
    private final PolicyService policyService;
    
    @GetMapping
    public ResponseEntity<List<PolicyDTO>> getAllPolicies() {
        List<PolicyDTO> policies = policyService.getAllPolicies();
        return ResponseEntity.ok(policies);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PolicyDTO> getPolicyById(@PathVariable Long id) {
        return policyService.getPolicyById(id)
                .map(policy -> ResponseEntity.ok(policy))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PolicyDTO>> getPoliciesByUserId(@PathVariable Long userId) {
        List<PolicyDTO> policies = policyService.getPoliciesByUserId(userId);
        return ResponseEntity.ok(policies);
    }
    
    @GetMapping("/number/{policyNumber}")
    public ResponseEntity<PolicyDTO> getPolicyByPolicyNumber(@PathVariable String policyNumber) {
        return policyService.getPolicyByPolicyNumber(policyNumber)
                .map(policy -> ResponseEntity.ok(policy))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<PolicyDTO> createPolicy(@RequestBody CreatePolicyDTO createPolicyDTO) {
        try {
            PolicyDTO createdPolicy = policyService.createPolicy(createPolicyDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPolicy);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PolicyDTO> updatePolicy(@PathVariable Long id, @RequestBody CreatePolicyDTO updatePolicyDTO) {
        return policyService.updatePolicy(id, updatePolicyDTO)
                .map(policy -> ResponseEntity.ok(policy))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<PolicyDTO> updatePolicyStatus(@PathVariable Long id, @RequestParam Policy.PolicyStatus status) {
        return policyService.updatePolicyStatus(id, status)
                .map(policy -> ResponseEntity.ok(policy))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePolicy(@PathVariable Long id) {
        boolean deleted = policyService.deletePolicy(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
