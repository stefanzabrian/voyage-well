package com.dev.voyagewell.service.user.password;

import com.dev.voyagewell.controller.dto.recover.ResetPasswordDto;
import com.dev.voyagewell.controller.dto.recover.UpdatePasswordDto;
import com.dev.voyagewell.configuration.utils.exception.ResourceNotFoundException;

import javax.mail.MessagingException;

public interface PasswordService {
    void forgotPassword(String email) throws MessagingException, ResourceNotFoundException;
    String getForgotPasswordToken();
    String getChangePasswordToken();
    void resetPassword(String token, ResetPasswordDto resetPasswordDto) throws MessagingException, ResourceNotFoundException;
    void verifyIdentity(String email, String password) throws ResourceNotFoundException, MessagingException;
    void updatePassword(String token, UpdatePasswordDto updatePasswordDto) throws MessagingException, ResourceNotFoundException;
}
