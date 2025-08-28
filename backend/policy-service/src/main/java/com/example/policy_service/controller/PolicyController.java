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
    @Operation(
            summary = "Tüm poliçeleri listele",
            description = "Sistemdeki tüm sigorta poliçelerini kullanıcı bilgileri ile birlikte getirir. " +
                         "Her poliçe için user bilgileri Feign Client aracılığıyla user-service'den çekilir."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Poliçeler başarıyla listelendi",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = PolicyDTO.class)))
    })
    public ResponseEntity<List<PolicyDTO>> getAllPolicies() {
        List<PolicyDTO> policies = policyService.getAllPolicies();
        return ResponseEntity.ok(policies);
    }
    
    @GetMapping("/{id}")
    @Operation(
            summary = "ID'ye göre poliçe getir",
            description = "Belirtilen ID'ye sahip poliçenin detaylarını kullanıcı bilgileri ile birlikte getirir."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Poliçe başarıyla bulundu",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = PolicyDTO.class))),
            @ApiResponse(responseCode = "404", description = "Poliçe bulunamadı")
    })
    public ResponseEntity<PolicyDTO> getPolicyById(
            @Parameter(description = "Poliçe ID'si", required = true, example = "1")
            @PathVariable Long id) {
        return policyService.getPolicyById(id)
                .map(policy -> ResponseEntity.ok(policy))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/user/{userId}")
    @Operation(
            summary = "Kullanıcıya ait poliçeleri listele",
            description = "Belirtilen kullanıcı ID'sine ait tüm sigorta poliçelerini getirir. " +
                         "Bu endpoint ile bir kullanıcının sahip olduğu tüm poliçeleri görebilirsiniz."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Kullanıcının poliçeleri başarıyla listelendi",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = PolicyDTO.class)))
    })
    public ResponseEntity<List<PolicyDTO>> getPoliciesByUserId(
            @Parameter(description = "Kullanıcı ID'si", required = true, example = "1")
            @PathVariable Long userId) {
        List<PolicyDTO> policies = policyService.getPoliciesByUserId(userId);
        return ResponseEntity.ok(policies);
    }
    
    @GetMapping("/number/{policyNumber}")
    @Operation(
            summary = "Poliçe numarasına göre poliçe getir",
            description = "Poliçe numarası ile poliçe detaylarını getirir. " +
                         "Poliçe numarası sistem tarafından otomatik oluşturulan benzersiz bir koddur (örn: POL000001)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Poliçe başarıyla bulundu",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = PolicyDTO.class))),
            @ApiResponse(responseCode = "404", description = "Poliçe numarası bulunamadı")
    })
    public ResponseEntity<PolicyDTO> getPolicyByPolicyNumber(
            @Parameter(description = "Poliçe numarası", required = true, example = "POL000001")
            @PathVariable String policyNumber) {
        return policyService.getPolicyByPolicyNumber(policyNumber)
                .map(policy -> ResponseEntity.ok(policy))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @Operation(
            summary = "Yeni poliçe oluştur",
            description = "Yeni bir sigorta poliçesi oluşturur. Poliçe numarası otomatik olarak sistem tarafından atanır. " +
                         "Kullanıcı ID'si geçerli olmalı ve user-service'de mevcut olmalıdır."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Poliçe başarıyla oluşturuldu",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = PolicyDTO.class))),
            @ApiResponse(responseCode = "400", description = "Geçersiz veri gönderildi")
    })
    public ResponseEntity<PolicyDTO> createPolicy(
            @Parameter(description = "Yeni poliçe bilgileri", required = true)
            @RequestBody CreatePolicyDTO createPolicyDTO) {
        try {
            PolicyDTO createdPolicy = policyService.createPolicy(createPolicyDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPolicy);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    @Operation(
            summary = "Poliçe bilgilerini güncelle",
            description = "Mevcut bir poliçenin bilgilerini günceller. " +
                         "Poliçe numarası ve kullanıcı ID'si değiştirilemez, sadece poliçe detayları güncellenir."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Poliçe başarıyla güncellendi",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = PolicyDTO.class))),
            @ApiResponse(responseCode = "404", description = "Poliçe bulunamadı")
    })
    public ResponseEntity<PolicyDTO> updatePolicy(
            @Parameter(description = "Güncellenecek poliçe ID'si", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Güncellenecek poliçe bilgileri", required = true)
            @RequestBody CreatePolicyDTO updatePolicyDTO) {
        return policyService.updatePolicy(id, updatePolicyDTO)
                .map(policy -> ResponseEntity.ok(policy))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}/status")
    @Operation(
            summary = "Poliçe durumunu güncelle",
            description = "Bir poliçenin durumunu günceller. " +
                         "Geçerli durumlar: ACTIVE (Aktif), INACTIVE (Pasif), EXPIRED (Süresi Dolmuş), CANCELLED (İptal Edilmiş)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Poliçe durumu başarıyla güncellendi",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = PolicyDTO.class))),
            @ApiResponse(responseCode = "404", description = "Poliçe bulunamadı")
    })
    public ResponseEntity<PolicyDTO> updatePolicyStatus(
            @Parameter(description = "Poliçe ID'si", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Yeni poliçe durumu", required = true, example = "ACTIVE")
            @RequestParam Policy.PolicyStatus status) {
        return policyService.updatePolicyStatus(id, status)
                .map(policy -> ResponseEntity.ok(policy))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Poliçe sil",
            description = "Belirtilen ID'ye sahip poliçeyi sistemden kalıcı olarak siler. " +
                         "Bu işlem geri alınamaz. Silme işlemi yerine poliçe durumunu CANCELLED olarak güncellemeyi düşünün."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Poliçe başarıyla silindi"),
            @ApiResponse(responseCode = "404", description = "Silinecek poliçe bulunamadı")
    })
    public ResponseEntity<Void> deletePolicy(
            @Parameter(description = "Silinecek poliçe ID'si", required = true, example = "1")
            @PathVariable Long id) {
        boolean deleted = policyService.deletePolicy(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
