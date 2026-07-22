package io.github.hyunjindevv.conn.security.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JsonAuthenticationFailureHandler
        implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    public JsonAuthenticationFailureHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException {

        int status;
        LoginFailureResponse body;

        if (exception instanceof InvalidLoginRequestException) {
            status = HttpServletResponse.SC_BAD_REQUEST;
            body = new LoginFailureResponse(
                    "INVALID_LOGIN_REQUEST",
                    "로그인 요청 형식이 올바르지 않습니다."
            );
        } else {
            status = HttpServletResponse.SC_UNAUTHORIZED;
            body = new LoginFailureResponse(
                    "INVALID_CREDENTIALS",
                    "아이디 또는 비밀번호가 올바르지 않습니다."
            );
        }

        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        objectMapper.writeValue(
                response.getOutputStream(),
                body
        );

    }
}
