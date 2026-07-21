package io.github.hyunjindevv.conn.security.authentication;

import io.github.hyunjindevv.conn.role.domain.RoleStatus;
import io.github.hyunjindevv.conn.user.domain.User;
import io.github.hyunjindevv.conn.user.repository.UserRepository;
import io.github.hyunjindevv.conn.user.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConnUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final String companyCode;

    public ConnUserDetailsService(
            UserRepository userRepository,
            UserRoleRepository userRoleRepository,
            @Value("${conn.security.default-company-code}") String companyCode
    ) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.companyCode = companyCode;
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        // 1. 사용자 조회
        User user = userRepository
                .findByCompany_CodeAndLoginIdIgnoreCase(companyCode, loginId)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Invalid login credentials")
                );

        // 2. 활성 역할 코드 조회
        List<String> roleCodes = userRoleRepository.findRoleCodes(
                companyCode,
                user.getLoginId(),
                RoleStatus.ACTIVE
        );

        // 3. Spring Security 권한으로 변환
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (String roleCode : roleCodes) {
            authorities.add(
                    new SimpleGrantedAuthority("ROLE_" + roleCode)
            );
        }

        // 4. Principal 반환
        ConnUserPrincipal principal = new ConnUserPrincipal(
                user.getId(),
                companyCode,
                user.getLoginId(),
                user.getName(),
                authorities,
                user.getStatus(),
                user.getPasswordHash()
        );
        return principal;
    }
}
