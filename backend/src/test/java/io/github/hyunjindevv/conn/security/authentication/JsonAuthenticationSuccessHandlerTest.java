package io.github.hyunjindevv.conn.security.authentication;

import io.github.hyunjindevv.conn.user.domain.UserStatus;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonAuthenticationSuccessHandlerTest {

    @Test
    void writesAuthenticatedUserAsJsonResponse() throws IOException {

        ConnUserPrincipal principal = new ConnUserPrincipal(
                1L,
                "CONN",
                "manager01",
                "생산부장",
                List.of(
                        new SimpleGrantedAuthority("ROLE_EMPLOYEE"),
                        new SimpleGrantedAuthority("ITEM_READ"),
                        new SimpleGrantedAuthority("ROLE_DEPARTMENT_MANAGER")
                ),
                UserStatus.ACTIVE,
                null
        );

        Authentication authentication =
                UsernamePasswordAuthenticationToken.authenticated(
                        principal,
                        null,
                        principal.getAuthorities()
                );

        ObjectMapper objectMapper = new ObjectMapper();
        JsonAuthenticationSuccessHandler handler =
                new JsonAuthenticationSuccessHandler(objectMapper);

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        handler.onAuthenticationSuccess(request, response, authentication);

        LoginSuccessResponse body = objectMapper.readValue(
                response.getContentAsString(),
                LoginSuccessResponse.class
        );

        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        assertEquals(1L, body.userId());
        assertEquals("CONN", body.companyCode());
        assertEquals("manager01", body.loginId());
        assertEquals("생산부장", body.name());
        assertEquals(
                List.of("DEPARTMENT_MANAGER", "EMPLOYEE"),
                body.roles()
        );
    }
}
