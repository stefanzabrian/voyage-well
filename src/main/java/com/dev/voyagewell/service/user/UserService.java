package com.dev.voyagewell.service.user;

import com.dev.voyagewell.controller.dto.register.RegisterDto;
import com.dev.voyagewell.model.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    Optional<User> findByEmail(String email);
    void create(RegisterDto newUser);
}
