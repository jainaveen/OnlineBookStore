package com.example.OnlineBookstore.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private String testUsername = "testuser";
    private String validToken;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        // Generate a valid token for testing
        validToken = jwtUtil.generateToken(testUsername);
    }

    /* @Test
    void testGenerateToken() {
        String token = jwtUtil.generateToken(testUsername);
        assertNotNull(token);
        assertTrue(token.startsWith("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")); // JWT header
    }

     */

    @Test
    void testExtractUsername() {
        String username = jwtUtil.extractUsername(validToken);
        assertEquals(testUsername, username);
    }

    @Test
    void testValidateToken_ValidToken() {
        boolean isValid = jwtUtil.validateToken(validToken);
        assertTrue(isValid);
    }

   /* @Test
    void testValidateToken_ExpiredToken() throws InterruptedException {
        // Set an expired token (token valid for 1 hour)
        String expiredToken = jwtUtil.generateToken(testUsername);
      //  Thread.sleep(1000 * 60 * 60 + 1000); // Wait until the token expires

        boolean isValid = jwtUtil.validateToken(expiredToken);
        assertFalse(isValid);
    }


    */
    @Test
    void testValidateToken_InvalidToken() {
        String invalidToken = validToken.substring(0, validToken.length() - 2) + "XX"; // Modify token
        assertThrows(JwtException.class, () -> jwtUtil.extractUsername(invalidToken));
    }

    @Test
    void testIsTokenExpired() {
        boolean isExpired = jwtUtil.isTokenExpired(validToken);
        assertFalse(isExpired);  // The token should not be expired immediately after generation
    }

    @Test
    void testExtractAllClaims() {
        Claims claims = jwtUtil.extractAllClaims(validToken);
        assertNotNull(claims);
        assertEquals(testUsername, claims.getSubject());
    }
}
