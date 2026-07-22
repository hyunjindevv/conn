package io.github.hyunjindevv.conn.security.authentication;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class JsonAuthenticationFailureHandlerTest {

    @Test
    void writesSafeJsonResponseForAuthenticationFailure() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonAuthenticationFailureHandler handler =
                new JsonAuthenticationFailureHandler(objectMapper);

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        BadCredentialsException exception =
                new BadCredentialsException("internal authentication details");

        handler.onAuthenticationFailure(request, response, exception);

        LoginFailureResponse body = objectMapper.readValue(
                response.getContentAsByteArray(),
                LoginFailureResponse.class
        );

        assertEquals(
                HttpServletResponse.SC_UNAUTHORIZED,
                response.getStatus()
        );
        assertEquals("INVALID_CREDENTIALS", body.code());
        assertEquals(
                "아이디 또는 비밀번호가 올바르지 않습니다.",
                body.message()
        );
        assertFalse(
                response.getContentAsString()
                        .contains("internal authentication details")
        );
    }

    @Test
    void writesBadRequestForInvalidLoginRequest() throws IOException {

        InvalidLoginRequestException exception =
                new InvalidLoginRequestException(
                        "internal parser details",
                        new IllegalArgumentException("raw JSON error")
                );

        ObjectMapper objectMapper = new ObjectMapper();
        JsonAuthenticationFailureHandler handler =
                new JsonAuthenticationFailureHandler(objectMapper);

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();


        handler.onAuthenticationFailure(request, response, exception);

        LoginFailureResponse body = objectMapper.readValue(
                response.getContentAsByteArray(),
                LoginFailureResponse.class
        );

        assertEquals(
                HttpServletResponse.SC_BAD_REQUEST,
                response.getStatus()
        );
        assertEquals("INVALID_LOGIN_REQUEST", body.code());
        assertEquals(
                "로그인 요청 형식이 올바르지 않습니다.",
                body.message()
        );
        assertFalse(
                response.getContentAsString()
                        .contains("internal parser details")
        );
        assertFalse(
                response.getContentAsString()
                        .contains("raw JSON error")
        );
    }
}
