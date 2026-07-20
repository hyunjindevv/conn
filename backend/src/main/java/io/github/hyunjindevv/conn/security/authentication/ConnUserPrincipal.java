package io.github.hyunjindevv.conn.security.authentication;

import io.github.hyunjindevv.conn.user.domain.UserStatus;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class ConnUserPrincipal implements UserDetails, CredentialsContainer {


    private final Long userId;
    private final String companyCode;
    private final String loginId;
    private final String name;
    private final List<GrantedAuthority> authorities;
    private final UserStatus status;
    private String passwordHash;

    public ConnUserPrincipal(Long userId, String companyCode, String loginId, String name, List<GrantedAuthority> authorities, UserStatus status, String passwordHash) {
        this.userId = userId;
        this.companyCode = companyCode;
        this.loginId = loginId;
        this.name = name;
        this.authorities = List.copyOf(authorities);
        this.status = status;
        this.passwordHash = passwordHash;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public @Nullable String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return loginId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public String getName() {
        return name;
    }

    public UserStatus getStatus() {
        return status;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status != UserStatus.LOCKED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status == UserStatus.ACTIVE;
    }


    @Override
    public void eraseCredentials() {
        passwordHash = null;
    }
}
