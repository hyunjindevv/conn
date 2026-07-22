package io.github.hyunjindevv.conn.security.authentication;

import java.util.List;

public record LoginSuccessResponse(
        Long userId,
        String companyCode,
        String loginId,
        String name,
        List<String> roles
) {
    public LoginSuccessResponse {
        roles = List.copyOf(roles);
    }
}
