package io.github.hyunjindevv.conn.user.repository;

import io.github.hyunjindevv.conn.role.domain.RoleStatus;
import io.github.hyunjindevv.conn.user.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    @Query("""
                    SELECT ur.role.code
                    FROM UserRole ur
                    WHERE ur.company.code = :companyCode
                      AND LOWER(ur.user.loginId) = LOWER(:loginId)
                      AND ur.role.status = :roleStatus
                    ORDER BY ur.role.sortOrder, ur.role.id
            """)
    List<String> findRoleCodes(
            @Param("companyCode") String companyCode,
            @Param("loginId") String loginId,
            @Param("roleStatus") RoleStatus roleStatus
    );
}
