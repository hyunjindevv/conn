package io.github.hyunjindevv.conn.user.repository;

import io.github.hyunjindevv.conn.role.domain.RoleStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRoleRepositoryTest {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Test
    void findsActiveRoleCodesInSortOrder() {
        List<String> roleCodes = userRoleRepository.findRoleCodes(
                "CONN",
                "MANAGER01",
                RoleStatus.ACTIVE
        );

        assertEquals(
                List.of("EMPLOYEE", "DEPARTMENT_MANAGER"),
                roleCodes
        );
    }
}
