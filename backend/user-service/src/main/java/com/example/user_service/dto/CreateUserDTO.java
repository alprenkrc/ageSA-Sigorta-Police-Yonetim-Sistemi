package com.example.user_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Kullanıcı oluşturma DTO'su")
public class CreateUserDTO {
    
    @Schema(description = "Email adresi", example = "john.doe@example.com", required = true)
    private String email;
    
    @Schema(description = "Ad", example = "John", required = true)
    private String firstName;
    
    @Schema(description = "Soyad", example = "Doe", required = true)
    private String lastName;
    
    @Schema(description = "Şifre", example = "password123", required = true)
    private String password;
    
    @Schema(description = "Telefon numarası", example = "+905551234567", required = true)
    private String phoneNumber;
    
    @Schema(description = "TC Kimlik Numarası", example = "12345678901", required = true)
    private String tckn;
    
    @Schema(description = "Adres", example = "İstanbul, Türkiye")
    private String address;
}
