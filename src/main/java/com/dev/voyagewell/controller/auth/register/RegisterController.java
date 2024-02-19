package com.dev.voyagewell.controller.auth.register;

import com.dev.voyagewell.controller.dto.register.RegisterDto;
import com.dev.voyagewell.model.user.Role;
import com.dev.voyagewell.service.user.UserService;
import com.dev.voyagewell.service.user.role.RoleService;
import com.dev.voyagewell.utils.exception.ErrorDetails;
import com.dev.voyagewell.utils.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;
import java.util.Date;

@RestController
@RequestMapping("/register")
@Validated
public class RegisterController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public RegisterController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto registerDto, WebRequest request) {
        if (userService.findByEmail(registerDto.getEmail().trim()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ErrorDetails(
                            new Date(),
                            "Email already exists",
                            request.getDescription(false)));
        }
        RegisterDto newUser = new RegisterDto();
        newUser.setEmail(registerDto.getEmail());
        newUser.setPassword(registerDto.getPassword());
        newUser.setFirstName(registerDto.getFirstName());
        newUser.setLastName(registerDto.getLastName());
        newUser.setNickName(registerDto.getNickName());
        newUser.setAcceptedConditions(registerDto.isAcceptedConditions());
        try {
            Role role = roleService.findRoleByName("USER");
            newUser.setRoles(Collections.singletonList(role));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDetails(new Date(), e.getMessage(), request.getDescription(false)));
        }

        try {
            userService.create(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body("User Created!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDetails(new Date(), e.getMessage(), request.getDescription(false)));
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDetails(new Date(), e.getMessage(), request.getDescription(false)));
        }
    }
}
