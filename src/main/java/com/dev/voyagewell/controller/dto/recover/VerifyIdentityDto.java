package com.dev.voyagewell.controller.dto.recover;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class VerifyIdentityDto {
    @NotBlank
    @NotNull
    @Size(min = 4, max = 15, message = "Password size must be between 4 and 15")
    private String password;
}
