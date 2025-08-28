package com.example.user_service.util;

import org.springframework.stereotype.Component;

/**
 * TC Kimlik Numarası doğrulama utility sınıfı
 * Türkiye Cumhuriyeti kimlik numarası algoritmasına göre doğrulama yapar
 */
@Component
public class TCKNValidator {
    
    /**
     * TC Kimlik Numarasının geçerli olup olmadığını kontrol eder
     * 
     * @param tckn 11 haneli TC Kimlik Numarası
     * @return geçerli ise true, değilse false
     */
    public static boolean isValid(String tckn) {
        if (tckn == null || tckn.trim().isEmpty()) {
            return false;
        }
        
        // Boşlukları temizle
        tckn = tckn.trim();
        
        // 11 hane olmalı
        if (tckn.length() != 11) {
            return false;
        }
        
        // Sadece rakam olmalı
        if (!tckn.matches("\\d{11}")) {
            return false;
        }
        
        // İlk hane 0 olamaz
        if (tckn.charAt(0) == '0') {
            return false;
        }
        
        // Tüm haneler aynı olamaz
        if (tckn.chars().distinct().count() == 1) {
            return false;
        }
        
        // TC Kimlik No algoritması
        int[] digits = new int[11];
        for (int i = 0; i < 11; i++) {
            digits[i] = Character.getNumericValue(tckn.charAt(i));
        }
        
        // İlk 10 hanenin toplamı
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += digits[i];
        }
        
        // 11. hane, ilk 10 hanenin toplamının 10'a bölümünden kalan olmalı
        if (sum % 10 != digits[10]) {
            return false;
        }
        
        // 1, 3, 5, 7, 9. hanelerin toplamının 7 katı ile 2, 4, 6, 8. hanelerin toplamının farkının 10'a bölümünden kalan 10. hane olmalı
        int oddSum = digits[0] + digits[2] + digits[4] + digits[6] + digits[8];
        int evenSum = digits[1] + digits[3] + digits[5] + digits[7];
        
        if ((oddSum * 7 - evenSum) % 10 != digits[9]) {
            return false;
        }
        
        return true;
    }
    
    /**
     * TC Kimlik Numarasını standart formata getirir (sadece rakamlar)
     * 
     * @param tckn formatlanacak TC Kimlik Numarası
     * @return formatlanmış TC Kimlik Numarası
     */
    public static String formatTCKN(String tckn) {
        if (tckn == null) {
            return null;
        }
        return tckn.replaceAll("[^0-9]", "");
    }
    
    /**
     * TC Kimlik Numarasını doğrular ve formatlar
     * 
     * @param tckn doğrulanacak TC Kimlik Numarası
     * @return geçerli ise formatlanmış TCKN, değilse null
     */
    public static String validateAndFormat(String tckn) {
        String formatted = formatTCKN(tckn);
        return isValid(formatted) ? formatted : null;
    }
}
