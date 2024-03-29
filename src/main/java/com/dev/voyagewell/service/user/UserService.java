package com.dev.voyagewell.service.user;

import com.dev.voyagewell.controller.dto.register.RegisterDto;
import com.dev.voyagewell.controller.dto.user.UserProfileDto;
import com.dev.voyagewell.model.user.User;
import com.dev.voyagewell.configuration.utils.exception.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.mail.MessagingException;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    Optional<User> findByEmail(String email);
    void create(RegisterDto newUser);
    void delete(int id, String authId) throws ResourceNotFoundException;
    UserProfileDto getProfileDtoByEmail(String email) throws ResourceNotFoundException;
    void validateString(String value, String fieldName);
    void profileUpdate(String email, UserProfileDto profileDto) throws ResourceNotFoundException;
    String getAvatarUrl(String email);
    String getNickName(String email);
    void updatePassword(String email, String password) throws ResourceNotFoundException, MessagingException;
}
