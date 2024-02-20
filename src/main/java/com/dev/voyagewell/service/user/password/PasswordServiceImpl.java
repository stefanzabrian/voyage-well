package com.dev.voyagewell.service.user.password;

import com.dev.voyagewell.configuration.jwt.JwtGenerator;
import com.dev.voyagewell.model.user.User;
import com.dev.voyagewell.service.mail.MailService;
import com.dev.voyagewell.service.user.UserService;
import com.dev.voyagewell.utils.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import javax.mail.MessagingException;
import java.util.Optional;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PasswordServiceImpl implements PasswordService {
    private String passwordToken = "";
    private final JwtGenerator jwtGenerator;
    private final MailService mailService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public PasswordServiceImpl(JwtGenerator jwtGenerator, MailService mailService, UserService userService, AuthenticationManager authenticationManager) {
        this.jwtGenerator = jwtGenerator;
        this.mailService = mailService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void forgotPassword(String email) throws MessagingException, ResourceNotFoundException {
        Optional<User> user = userService.findByEmail(email);
        if(user.isPresent()) {
            passwordToken = jwtGenerator.generateForgotPassToken(email);
            try {
                mailService.sendEmail("voyage-well@gmail.com",
                        email,
                        "Forgot Password requested a reset Link",
                        "Click the following link to reset your password : http://localhost:5173/reset-password?token=" + passwordToken);
            } catch (MessagingException e) {
                e.printStackTrace();
                throw new MessagingException("Failed to send mail");
            }
        } else {
            throw new ResourceNotFoundException("User not found");
        }
    }

    @Override
    public void resetPasswordToken() {
        this.passwordToken = "";
    }

    @Override
    public String getPasswordToken() {
        return passwordToken;
    }
}