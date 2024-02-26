package com.dev.voyagewell.controller.dto.recover;

import com.dev.voyagewell.configuration.utils.validator.FieldMatch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@FieldMatch.List({
        @FieldMatch(first = "newPassword", second = "confirmPassword", message = "Password must match")
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdatePasswordDto {
    @NotBlank
    @NotNull
    @Size(min = 4, max = 15, message = "Password size must be between 4 and 15")
    private String newPassword;
    @NotBlank
    @NotNull
    @Size(min = 4, max = 15, message = "Password size must be between 4 and 15")
    private String confirmPassword;
}
