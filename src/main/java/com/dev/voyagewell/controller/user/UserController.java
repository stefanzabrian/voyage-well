package com.dev.voyagewell.controller.user;

import com.dev.voyagewell.controller.dto.user.UserProfileDto;
import com.dev.voyagewell.service.user.UserService;
import com.dev.voyagewell.service.user.client.ClientService;
import com.dev.voyagewell.utils.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final ClientService clientService;

    @Autowired
    public UserController(UserService userService, ClientService clientService) {
        this.userService = userService;
        this.clientService = clientService;
    }

    @GetMapping("/profile-view")
    public ResponseEntity<?> profileView(Principal principal) {
        try {
            UserProfileDto profileDto = userService.getProfileDtoByEmail(principal.getName());
            return ResponseEntity.status(HttpStatus.OK).body(profileDto);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
