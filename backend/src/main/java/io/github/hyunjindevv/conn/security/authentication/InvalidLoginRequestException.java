package io.github.hyunjindevv.conn.security.authentication;

import org.springframework.security.authentication.AuthenticationServiceException;

public final class InvalidLoginRequestException extends AuthenticationServiceException {

    public InvalidLoginRequestException(
            String message,
            Throwable cause
    ) {
        super(message, cause);
    }
}
