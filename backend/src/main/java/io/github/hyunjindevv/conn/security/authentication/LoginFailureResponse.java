package io.github.hyunjindevv.conn.security.authentication;

public record LoginFailureResponse(
        String code,
        String message
) {
}
