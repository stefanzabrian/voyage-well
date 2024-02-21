package com.dev.voyagewell.configuration.jwt;

import com.dev.voyagewell.utils.exception.JwtValidationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;

@Component
public class JwtGenerator {
    public String generateToken(Authentication authentication) {
        String email = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);

        String token = Jwts
                .builder()
                .setSubject(email)
                .claim("authorities", authorities)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.JWT_SECRET)
                .compact();
        return token;
    }
    public String generateChangePasswordToken(Authentication authentication) {
        String email = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);

        String token = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512,SecurityConstants.CHANGE_PASSWORD_JWT_SECRET)
                .compact();
        return token;
    }
    public String generateForgotPassToken(String email) {
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);

        String token = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512,SecurityConstants.FORGOT_PASSWORD_JWT_SECRET)
                .compact();
        return token;
    }

    public String getEmailFromJwt(String token) {
        Claims claims = Jwts
                .parser()
                .setSigningKey(SecurityConstants.JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
    public String getEmailFromForgotPasswordJwt(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstants.FORGOT_PASSWORD_JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
    public String getEmailFromChangePasswordJwt(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstants.CHANGE_PASSWORD_JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts
                    .parser()
                    .setSigningKey(SecurityConstants.JWT_SECRET)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new JwtValidationException("Jwt was expired or incorrect :)");
        }
    }

    public boolean validateForgotPasswordToken(String token){
        try {
            Jwts
                    .parser()
                    .setSigningKey(SecurityConstants.FORGOT_PASSWORD_JWT_SECRET)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new JwtValidationException("Forgot-Password-Token was expired or incorrect");
        }
    }public boolean validateChangePasswordToken(String token){
        try {
            Jwts
                    .parser()
                    .setSigningKey(SecurityConstants.CHANGE_PASSWORD_JWT_SECRET)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new JwtValidationException("Change-Password-Token was expired or incorrect");
        }
    }

}
