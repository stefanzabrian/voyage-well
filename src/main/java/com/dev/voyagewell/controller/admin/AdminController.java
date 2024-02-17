package com.dev.voyagewell.controller.admin;

import com.dev.voyagewell.service.user.UserService;
import com.dev.voyagewell.utils.exception.JwtValidationException;
import com.dev.voyagewell.utils.exception.ResourceNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @DeleteMapping("/user/delete/{id}")
    @ExceptionHandler(JwtValidationException.class)
    public ResponseEntity<?> userDelete(@PathVariable(name = "id") int id, Principal principal) throws JsonProcessingException {
        try {
            userService.delete(id, principal.getName());
            return ResponseEntity.status(HttpStatus.OK).body("User deleted with id: " + id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User don't exists with id: " + id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
}
