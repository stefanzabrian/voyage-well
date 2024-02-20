package com.dev.voyagewell.configuration.jwt;

public class SecurityConstants {
    public static final long JWT_EXPIRATION = 3600000L;
    public static final String JWT_SECRET = "!@SecretJwtKey@!";
    public static final String FORGOT_PASSWORD_JWT_SECRET = "!@ForgotPasswordJwtKey@!";
}
