package com.dev.voyagewell.service.user;

import com.dev.voyagewell.controller.dto.register.RegisterDto;
import com.dev.voyagewell.controller.dto.user.UserProfileDto;
import com.dev.voyagewell.model.user.User;
import com.dev.voyagewell.utils.exception.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    Optional<User> findByEmail(String email);
    void create(RegisterDto newUser);
    void delete(int id, String authId) throws ResourceNotFoundException;
    UserProfileDto getProfileDtoByEmail(String email) throws ResourceNotFoundException;
}
