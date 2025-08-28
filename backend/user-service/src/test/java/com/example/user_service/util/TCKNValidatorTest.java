package com.example.user_service.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TCKNValidatorTest {
    
    @Test
    public void testValidTCKN() {
        // Valid TCKN examples (these are mathematically valid but not real identities)
        assertTrue(TCKNValidator.isValid("12345678901"));
        assertTrue(TCKNValidator.isValid("98765432109"));
    }
    
    @Test
    public void testInvalidTCKN() {
        // Null or empty
        assertFalse(TCKNValidator.isValid(null));
        assertFalse(TCKNValidator.isValid(""));
        assertFalse(TCKNValidator.isValid("   "));
        
        // Wrong length
        assertFalse(TCKNValidator.isValid("123456789"));
        assertFalse(TCKNValidator.isValid("123456789012"));
        
        // Starts with 0
        assertFalse(TCKNValidator.isValid("01234567890"));
        
        // Non-numeric
        assertFalse(TCKNValidator.isValid("1234567890a"));
        assertFalse(TCKNValidator.isValid("abcdefghijk"));
        
        // All same digits
        assertFalse(TCKNValidator.isValid("11111111111"));
        assertFalse(TCKNValidator.isValid("22222222222"));
        
        // Invalid checksum
        assertFalse(TCKNValidator.isValid("12345678900"));
        assertFalse(TCKNValidator.isValid("12345678902"));
    }
    
    @Test
    public void testFormatTCKN() {
        assertEquals("12345678901", TCKNValidator.formatTCKN("123 456 789 01"));
        assertEquals("12345678901", TCKNValidator.formatTCKN("123-456-789-01"));
        assertEquals("12345678901", TCKNValidator.formatTCKN("123.456.789.01"));
        assertEquals("12345678901", TCKNValidator.formatTCKN("  12345678901  "));
        assertNull(TCKNValidator.formatTCKN(null));
    }
    
    @Test
    public void testValidateAndFormat() {
        // Valid cases
        assertEquals("12345678901", TCKNValidator.validateAndFormat("123 456 789 01"));
        assertEquals("98765432109", TCKNValidator.validateAndFormat("987-654-321-09"));
        
        // Invalid cases
        assertNull(TCKNValidator.validateAndFormat("123456789"));
        assertNull(TCKNValidator.validateAndFormat("01234567890"));
        assertNull(TCKNValidator.validateAndFormat("11111111111"));
        assertNull(TCKNValidator.validateAndFormat(null));
    }
}
