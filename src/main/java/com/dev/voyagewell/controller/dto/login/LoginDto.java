package com.dev.voyagewell.controller.dto.login;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LoginDto {
    @NotNull(message = "Email must not be null")
    @NotBlank(message = "Email must not be blank")
    private String email;
    @NotNull(message = "Password must not be null")
    @NotBlank(message = "Password must not be blank")
    private String password;

}
