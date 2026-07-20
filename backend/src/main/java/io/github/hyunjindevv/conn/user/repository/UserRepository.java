package io.github.hyunjindevv.conn.user.repository;

import io.github.hyunjindevv.conn.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByCompany_CodeAndLoginIdIgnoreCase(String companyCode, String loginId);
}
