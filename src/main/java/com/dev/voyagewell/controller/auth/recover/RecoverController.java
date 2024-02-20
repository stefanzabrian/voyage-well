package com.dev.voyagewell.controller.auth.recover;

import com.dev.voyagewell.controller.dto.recover.RecoverDto;
import com.dev.voyagewell.controller.dto.recover.ResetPasswordDto;
import com.dev.voyagewell.service.user.UserService;
import com.dev.voyagewell.service.user.password.PasswordService;
import com.dev.voyagewell.utils.exception.ErrorDetails;
import com.dev.voyagewell.utils.exception.JwtValidationException;
import com.dev.voyagewell.utils.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.mail.MessagingException;
import java.util.Date;

@RestController
public class RecoverController {
    private final UserService userService;
    private final PasswordService passwordService;

    @Autowired
    public RecoverController(UserService userService, PasswordService passwordService) {
        this.userService = userService;
        this.passwordService = passwordService;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody RecoverDto recoverDto, WebRequest request) {
        try {
            passwordService.forgotPassword(recoverDto.getEmail());
            String token = passwordService.getPasswordToken();
            return ResponseEntity.status(HttpStatus.OK).body(token);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDetails(new Date(), e.getMessage(), request.getDescription(false)));
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDetails(new Date(), e.getMessage(), request.getDescription(false)));
        }
    }

    @PostMapping("reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam("token") String token, @Valid @RequestBody ResetPasswordDto resetPasswordDto, WebRequest request) {
        try {
            passwordService.resetPassword(token, resetPasswordDto);
            return ResponseEntity.status(HttpStatus.OK).body(new ErrorDetails(new Date(), "Password Updated Successfully", request.getDescription(false)));
        } catch (JwtValidationException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorDetails(new Date(), e.getMessage(), request.getDescription(false)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDetails(new Date(), e.getMessage(), request.getDescription(false)));
        } catch (ResourceNotFoundException | RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDetails(new Date(), e.getMessage(), request.getDescription(false)));
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ErrorDetails(new Date(), e.getMessage(), request.getDescription(false)));
        }
    }
}
