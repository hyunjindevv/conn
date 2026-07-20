package io.github.hyunjindevv.conn.user.repository;

import io.github.hyunjindevv.conn.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByCompany_CodeAndLoginIdIgnoreCase() {
        Optional<User> result = userRepository.findByCompany_CodeAndLoginIdIgnoreCase("CONN", "MANAGER01");

        assertTrue(result.isPresent());
    }
}
