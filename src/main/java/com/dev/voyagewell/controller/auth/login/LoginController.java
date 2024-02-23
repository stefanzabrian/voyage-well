package com.dev.voyagewell.controller.auth.login;

import com.dev.voyagewell.configuration.jwt.JwtGenerator;
import com.dev.voyagewell.controller.dto.auth.AuthResponseDto;
import com.dev.voyagewell.controller.dto.login.LoginDto;
import com.dev.voyagewell.service.user.UserService;
import com.dev.voyagewell.configuration.utils.exception.ErrorDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;


@RestController
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;
    private final UserService userService;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, JwtGenerator jwtGenerator, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
        this.userService = userService;
    }

    @RequestMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto, WebRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(),
                            loginDto.getPassword()
                    ));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(authentication);
            AuthResponseDto authResponseDto = new AuthResponseDto(token);
            authResponseDto.setAvatarUrl(userService.getAvatarUrl(loginDto.getEmail()));
            authResponseDto.setNickName(userService.getNickName(loginDto.getEmail()));
            return ResponseEntity.status(HttpStatus.OK).body(authResponseDto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorDetails(new Date(), e.getMessage(), request.getDescription(false)));
        }
    }

}
