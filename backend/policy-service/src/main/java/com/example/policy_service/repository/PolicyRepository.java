package com.example.policy_service.repository;

import com.example.policy_service.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {
    
    List<Policy> findByUserId(Long userId);
    
    Optional<Policy> findByPolicyNumber(String policyNumber);
    
    List<Policy> findByStatus(Policy.PolicyStatus status);
    
    List<Policy> findByType(Policy.PolicyType type);
    
    boolean existsByPolicyNumber(String policyNumber);
}
