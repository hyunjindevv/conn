package io.github.hyunjindevv.conn.security.authentication;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginRequestTest {

    @Test
    void toStringProtectsPassword() {
        LoginRequest loginRequest = new LoginRequest("manager01", "Conn1234!");

        String result = loginRequest.toString();

        assertTrue(result.contains("manager01"));
        assertTrue(result.contains("[PROTECTED]"));
        assertFalse(result.contains("Conn1234!"));
    }
}
