package com.dev.voyagewell.service.user.password;

import com.dev.voyagewell.utils.exception.ResourceNotFoundException;

import javax.mail.MessagingException;

public interface PasswordService {
    void forgotPassword(String email) throws MessagingException, ResourceNotFoundException;
    void resetPasswordToken();
    String getPasswordToken();
}
