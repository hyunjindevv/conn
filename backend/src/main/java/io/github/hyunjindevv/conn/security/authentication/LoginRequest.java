package io.github.hyunjindevv.conn.security.authentication;

public record LoginRequest(
        String loginId,
        String password
) {
    @Override
    public String toString() {
        return "LoginRequest[loginId=" + loginId + ", password=[PROTECTED]]";
    }
}
