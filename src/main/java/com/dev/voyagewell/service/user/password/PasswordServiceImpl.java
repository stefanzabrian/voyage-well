package com.dev.voyagewell.service.user.password;

import com.dev.voyagewell.configuration.jwt.JwtGenerator;
import com.dev.voyagewell.controller.dto.recover.ResetPasswordDto;
import com.dev.voyagewell.controller.dto.recover.UpdatePasswordDto;
import com.dev.voyagewell.model.user.User;
import com.dev.voyagewell.service.mail.MailService;
import com.dev.voyagewell.service.user.UserService;
import com.dev.voyagewell.configuration.utils.exception.JwtValidationException;
import com.dev.voyagewell.configuration.utils.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import javax.mail.MessagingException;
import java.util.Optional;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PasswordServiceImpl implements PasswordService {
    private String forgotPasswordToken = "";
    private String changePasswordToken = "";
    private final JwtGenerator jwtGenerator;
    private final MailService mailService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public PasswordServiceImpl(JwtGenerator jwtGenerator, MailService mailService, UserService userService, AuthenticationManager authenticationManager, BCryptPasswordEncoder passwordEncoder) {
        this.jwtGenerator = jwtGenerator;
        this.mailService = mailService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void forgotPassword(String email) throws MessagingException, ResourceNotFoundException {
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent()) {
            forgotPasswordToken = jwtGenerator.generateForgotPassToken(email);
            try {
                mailService.sendEmail("security@voyage-well.com",
                        email,
                        "Voyage-well Recover Account",
                        "Click the following link to reset your password ->     http://localhost:5173/reset-password?token=" + forgotPasswordToken);
            } catch (MessagingException e) {
                e.printStackTrace();
                throw new MessagingException("Failed to send mail");
            }
        } else {
            throw new ResourceNotFoundException("User not found");
        }
    }

    private void resetPasswordToken() {
        this.forgotPasswordToken = "";
    }

    private void resetChangePasswordToken() {
        this.changePasswordToken = "";
    }

    @Override
    public String getForgotPasswordToken() {
        return forgotPasswordToken;
    }

    @Override
    public String getChangePasswordToken() {
        return changePasswordToken;
    }

    @Override
    public void resetPassword(String token, ResetPasswordDto resetPasswordDto) throws MessagingException, ResourceNotFoundException {
        boolean validated = jwtGenerator.validateForgotPasswordToken(token);
        if (!validated) {
            throw new JwtValidationException("Forgot-Password-Token was expired or incorrect");
        }
        userService.validateString(resetPasswordDto.getPassword(), "Password");
        userService.validateString(resetPasswordDto.getConfirmPassword(), "Confirm password");

        String email = jwtGenerator.getEmailFromForgotPasswordJwt(token);
        userService.updatePassword(email, resetPasswordDto.getPassword());
        resetPasswordToken();
    }

    @Override
    public void verifyIdentity(String email, String password) throws ResourceNotFoundException, MessagingException {
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User don't exits with email: " + email));
        if (passwordEncoder.matches(password, user.getPassword())) {
            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                email,
                                password
                        ));
                changePasswordToken = jwtGenerator.generateChangePasswordToken(authentication);
                mailService.sendEmail(
                        "security@voyage-well.com",
                        email,
                        "Password Update Link",
                        "Click the following link to reset your password : http://localhost:5173/update-password?token=" + changePasswordToken
                );
            } catch (MessagingException e) {
                e.printStackTrace();
                throw new MessagingException("Failed to send 'Password Update Link' email");
            }
        } else {
            throw new IllegalArgumentException("Verification failed!");
        }
    }

    @Override
    public void updatePassword(String token, UpdatePasswordDto updatePasswordDto) throws MessagingException, ResourceNotFoundException {
        boolean validated = jwtGenerator.validateChangePasswordToken(token);
        if (!validated) {
            throw new JwtValidationException("Change-Password-Token was expired or incorrect");
        }

        userService.validateString(updatePasswordDto.getNewPassword(), "New Password");
        userService.validateString(updatePasswordDto.getConfirmPassword(), "Confirm Password");

        String email = jwtGenerator.getEmailFromChangePasswordJwt(token);
        userService.updatePassword(email, updatePasswordDto.getNewPassword());
        resetChangePasswordToken();
    }
}
