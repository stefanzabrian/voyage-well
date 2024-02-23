package com.dev.voyagewell.configuration.jwt;

import com.dev.voyagewell.service.user.UserService;
import com.dev.voyagewell.configuration.utils.exception.ErrorDetails;
import com.dev.voyagewell.configuration.utils.exception.JwtValidationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JwtGenerator jwtGenerator;
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getJwtFromRequest(request);

            if (StringUtils.hasText(token)) {
                if (jwtGenerator.validateToken(token)) {
                    String email = jwtGenerator.getEmailFromJwt(token);
                    UserDetails userDetails = userService.loadUserByUsername(email);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (JwtValidationException e) {
            handleJwtException(response, e);
        }
    }

    private void handleJwtException(HttpServletResponse response, JwtValidationException e) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                e.getMessage(),
                "Jwt Validation Failed!"
        );
        String json = new ObjectMapper().writeValueAsString(errorDetails);
        response.getWriter().write(json);
        response.getWriter().flush();
        response.getWriter().close();
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
