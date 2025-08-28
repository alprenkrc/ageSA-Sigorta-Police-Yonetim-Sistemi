package com.example.policy_service.dto;

import com.example.policy_service.entity.Policy;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Yeni poliçe oluşturma için gereken bilgiler")
public class CreatePolicyDTO {
    
    @Schema(description = "Poliçe sahibi kullanıcı ID'si", example = "1", required = true)
    private Long userId;
    
    @Schema(description = "Poliçe türü", example = "HEALTH", 
            allowableValues = {"HEALTH", "AUTO", "HOME", "LIFE", "TRAVEL"}, required = true)
    private Policy.PolicyType type;
    
    @Schema(description = "Sigorta teminat tutarı (TL)", example = "100000.00", required = true)
    private BigDecimal coverageAmount;
    
    @Schema(description = "Aylık prim tutarı (TL)", example = "250.50", required = true)
    private BigDecimal premium;
    
    @Schema(description = "Poliçe başlangıç tarihi", example = "2024-01-01", required = true)
    private LocalDate startDate;
    
    @Schema(description = "Poliçe bitiş tarihi", example = "2024-12-31", required = true)
    private LocalDate endDate;
    
    @Schema(description = "Poliçe açıklaması (isteğe bağlı)", example = "Kapsamlı sağlık sigortası")
    private String description;
}
