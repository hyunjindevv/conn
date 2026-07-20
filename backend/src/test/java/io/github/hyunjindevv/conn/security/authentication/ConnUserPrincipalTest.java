package io.github.hyunjindevv.conn.security.authentication;

import io.github.hyunjindevv.conn.user.domain.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConnUserPrincipalTest {

    @Test
    void activeUserIsEnabledAndCredentialsCanBeErased() {
        ConnUserPrincipal principal = new ConnUserPrincipal(
                1L,
                "CONN",
                "manager01",
                "생산부장",
                List.of(new SimpleGrantedAuthority("EMPLOYEE")),
                UserStatus.ACTIVE,
                "encoded-password"
        );

        assertTrue(principal.isEnabled());
        assertTrue(principal.isAccountNonLocked());
        assertTrue(principal.isAccountNonExpired());
        assertEquals("manager01", principal.getUsername());
        assertEquals("EMPLOYEE", principal.getAuthorities().iterator().next().getAuthority());

        principal.eraseCredentials();

        assertNull(principal.getPassword());
    }

    @Test
    void lockedUserCannotAuthenticate() {
        ConnUserPrincipal principal = new ConnUserPrincipal(
                1L,
                "CONN",
                "manager01",
                "생산부장",
                List.of(new SimpleGrantedAuthority("EMPLOYEE")),
                UserStatus.LOCKED,
                "encoded-password"
        );

        assertFalse(principal.isAccountNonLocked());
        assertFalse(principal.isEnabled());
    }
}
