package com.dev.voyagewell.controller.dto.recover;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    private String password;
}
