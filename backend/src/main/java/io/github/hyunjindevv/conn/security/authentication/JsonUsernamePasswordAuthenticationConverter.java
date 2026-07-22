package io.github.hyunjindevv.conn.security.authentication;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUsernamePasswordAuthenticationConverter implements AuthenticationConverter {

    private final ObjectMapper objectMapper;

    public JsonUsernamePasswordAuthenticationConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication convert(HttpServletRequest request) {

        try {
            LoginRequest loginRequest =
                    objectMapper.readValue(request.getInputStream(), LoginRequest.class);

            String loginId = loginRequest.loginId() == null
                    ? ""
                    : loginRequest.loginId().strip();

            String password = loginRequest.password() == null
                    ? ""
                    : loginRequest.password();

            return UsernamePasswordAuthenticationToken.unauthenticated(
                    loginId,
                    password
            );
        } catch (JacksonException | IOException e) {
            throw new InvalidLoginRequestException(
                    "Failed to read login request",
                    e
            );
        }
    }
}
