package com.dev.voyagewell.controller.user;

import com.dev.voyagewell.controller.dto.user.UserProfileDto;
import com.dev.voyagewell.service.user.UserService;
import com.dev.voyagewell.service.user.client.ClientService;
import com.dev.voyagewell.utils.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("/profile-update")
    public ResponseEntity<?> profileUpdate(Principal principal, @Valid @RequestBody UserProfileDto profileDto) {
        try {
            userService.validateString(profileDto.getFirstName(), "first name");
            userService.validateString(profileDto.getLastName(), "last name");
            userService.validateString(profileDto.getNickName(), "nick name");
            userService.validateString(profileDto.getEmail(), "email");
            userService.validateString(profileDto.getBioInfo(), "bio info");
            userService.validateString(profileDto.getPhoneNumber(), "phone number");
            userService.validateString(profileDto.getAvatarUrl(), "avatar url");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        try {
            userService.profileUpdate(principal.getName(), profileDto);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Profile updated");
    }
}
