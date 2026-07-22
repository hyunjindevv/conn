package io.github.hyunjindevv.conn.security.authentication;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import tools.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class JsonUsernamePasswordAuthenticationConverterTest {

    @Test
    void convertsJsonRequestToUnauthenticatedToken() {

        ObjectMapper objectMapper = new ObjectMapper();

        JsonUsernamePasswordAuthenticationConverter converter = new JsonUsernamePasswordAuthenticationConverter(objectMapper);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContentType(MediaType.APPLICATION_JSON_VALUE);
        request.setContent("""
                {
                    "loginId": " manager01 ",
                    "password": "Conn1234! "
                }
                """.getBytes(StandardCharsets.UTF_8));

        Authentication authentication = converter.convert(request);

        assertNotNull(authentication);
        assertFalse(authentication.isAuthenticated());
        assertEquals("manager01", authentication.getPrincipal());
        assertEquals("Conn1234! ", authentication.getCredentials());
    }

    @Test
    void throwsInvalidLoginRequestExceptionForInvalidJson() {

        ObjectMapper objectMapper = new ObjectMapper();

        JsonUsernamePasswordAuthenticationConverter converter = new JsonUsernamePasswordAuthenticationConverter(objectMapper);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContentType(MediaType.APPLICATION_JSON_VALUE);
        request.setContent(
                "{ invalid-json".getBytes(StandardCharsets.UTF_8)
        );

        InvalidLoginRequestException exception = assertThrows(
                InvalidLoginRequestException.class,
                () -> converter.convert(request)
        );

        assertEquals(
                "Failed to read login request",
                exception.getMessage()
        );
        assertNotNull(exception.getCause());
    }
}
