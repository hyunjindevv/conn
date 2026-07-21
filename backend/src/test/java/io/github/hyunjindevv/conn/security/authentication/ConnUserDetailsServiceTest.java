package io.github.hyunjindevv.conn.security.authentication;

import io.github.hyunjindevv.conn.user.domain.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("local")
class ConnUserDetailsServiceTest {

    @Autowired
    private ConnUserDetailsService userDetailsService;

    @Test
    void loadsUserWithActiveRoles() {
        UserDetails userDetails =
                userDetailsService.loadUserByUsername("MANAGER01");

        ConnUserPrincipal principal = assertInstanceOf(
                ConnUserPrincipal.class,
                userDetails
        );

        List<String> authorities = principal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        assertNotNull(principal.getUserId());
        assertEquals("CONN", principal.getCompanyCode());
        assertEquals("manager01", principal.getUsername());
        assertEquals("생산부장", principal.getName());
        assertEquals(UserStatus.ACTIVE, principal.getStatus());
        assertEquals(
                List.of("ROLE_EMPLOYEE", "ROLE_DEPARTMENT_MANAGER"),
                authorities
        );
    }

    @Test
    void rejectUnknownUser() {
        assertThrows(
                UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("unknown-user")
        );
    }
}
