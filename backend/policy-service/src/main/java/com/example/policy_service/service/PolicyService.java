package com.example.policy_service.service;

import com.example.policy_service.client.UserServiceClient;
import com.example.policy_service.dto.CreatePolicyDTO;
import com.example.policy_service.dto.PolicyDTO;
import com.example.policy_service.dto.UserDTO;
import com.example.policy_service.entity.Policy;
import com.example.policy_service.repository.PolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PolicyService {
    
    private final PolicyRepository policyRepository;
    private final UserServiceClient userServiceClient;
    
    public List<PolicyDTO> getAllPolicies() {
        return policyRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public Optional<PolicyDTO> getPolicyById(Long id) {
        return policyRepository.findById(id)
                .map(this::convertToDTO);
    }
    
    public List<PolicyDTO> getPoliciesByUserId(Long userId) {
        return policyRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public Optional<PolicyDTO> getPolicyByPolicyNumber(String policyNumber) {
        return policyRepository.findByPolicyNumber(policyNumber)
                .map(this::convertToDTO);
    }
    
    public PolicyDTO createPolicy(CreatePolicyDTO createPolicyDTO) {
        // Generate unique policy number
        String policyNumber = generatePolicyNumber();
        
        Policy policy = new Policy();
        policy.setPolicyNumber(policyNumber);
        policy.setUserId(createPolicyDTO.getUserId());
        policy.setType(createPolicyDTO.getType());
        policy.setCoverageAmount(createPolicyDTO.getCoverageAmount());
        policy.setPremium(createPolicyDTO.getPremium());
        policy.setStartDate(createPolicyDTO.getStartDate());
        policy.setEndDate(createPolicyDTO.getEndDate());
        policy.setDescription(createPolicyDTO.getDescription());
        policy.setStatus(Policy.PolicyStatus.ACTIVE);
        
        Policy savedPolicy = policyRepository.save(policy);
        return convertToDTO(savedPolicy);
    }
    
    public Optional<PolicyDTO> updatePolicy(Long id, CreatePolicyDTO updatePolicyDTO) {
        return policyRepository.findById(id)
                .map(policy -> {
                    policy.setType(updatePolicyDTO.getType());
                    policy.setCoverageAmount(updatePolicyDTO.getCoverageAmount());
                    policy.setPremium(updatePolicyDTO.getPremium());
                    policy.setStartDate(updatePolicyDTO.getStartDate());
                    policy.setEndDate(updatePolicyDTO.getEndDate());
                    policy.setDescription(updatePolicyDTO.getDescription());
                    return convertToDTO(policyRepository.save(policy));
                });
    }
    
    public boolean deletePolicy(Long id) {
        if (policyRepository.existsById(id)) {
            policyRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public Optional<PolicyDTO> updatePolicyStatus(Long id, Policy.PolicyStatus status) {
        return policyRepository.findById(id)
                .map(policy -> {
                    policy.setStatus(status);
                    return convertToDTO(policyRepository.save(policy));
                });
    }
    
    private PolicyDTO convertToDTO(Policy policy) {
        PolicyDTO dto = new PolicyDTO();
        dto.setId(policy.getId());
        dto.setPolicyNumber(policy.getPolicyNumber());
        dto.setUserId(policy.getUserId());
        dto.setType(policy.getType());
        dto.setCoverageAmount(policy.getCoverageAmount());
        dto.setPremium(policy.getPremium());
        dto.setStartDate(policy.getStartDate());
        dto.setEndDate(policy.getEndDate());
        dto.setStatus(policy.getStatus());
        dto.setDescription(policy.getDescription());
        dto.setCreatedAt(policy.getCreatedAt());
        dto.setUpdatedAt(policy.getUpdatedAt());
        
        // Fetch user information via Feign Client
        try {
            UserDTO user = userServiceClient.getUserById(policy.getUserId());
            dto.setUser(user);
        } catch (Exception e) {
            // Handle error gracefully - user service might be down
            dto.setUser(null);
        }
        
        return dto;
    }
    
    private String generatePolicyNumber() {
        String prefix = "POL";
        long count = policyRepository.count();
        return prefix + String.format("%06d", count + 1);
    }
}
