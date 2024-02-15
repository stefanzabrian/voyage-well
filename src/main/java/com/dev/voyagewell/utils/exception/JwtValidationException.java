package com.dev.voyagewell.utils.exception;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

public class JwtValidationException extends AuthenticationCredentialsNotFoundException {
    public JwtValidationException(String msg) {
        super(msg);
    }
}
