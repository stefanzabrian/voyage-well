package com.dev.voyagewell.controller.dto.register;

import com.dev.voyagewell.model.user.Role;
import com.dev.voyagewell.configuration.utils.validator.FieldMatch;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@FieldMatch.List({
        @FieldMatch(first = "password", second = "confirmPassword", message = "Password must match")
})
@NoArgsConstructor
@Getter
@Setter
public class RegisterDto {
    @Email(message = "Email must be a well-formed email address")
    @NotNull(message = "Email must not be null")
    @NotBlank(message = "Email must not be blank")
    private String email;
    @Size(min = 4, max = 15, message = "Password size must be between 4 and 15")
    @NotNull(message = "Password must not be null")
    @NotBlank(message = "Password must not be blank")
    private String password;
    @Size(min = 4, max = 15, message = "Confirm password size must be between 4 and 15")
    @NotNull(message = "Confirm password must not be null")
    @NotBlank(message = "Confirm password must not be blank")
    private String confirmPassword;
    @NotNull(message = "first name must not be null")
    @NotBlank(message = "first name must not be blank")
    private String firstName;
    @NotNull(message = "last name must not be null")
    @NotBlank(message = "last name must not be blank")
    private String lastName;
    @NotNull(message = "nick name must not be null")
    @NotBlank(message = "nick name must not be blank")
    private String nickName;
    @NotNull
    @JsonProperty
    private boolean acceptedConditions ;
    private List<Role> roles;

    @Override
    public String toString() {
        return "RegisterDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", acceptedConditions=" + acceptedConditions +
                ", roles=" + roles +
                '}';
    }
}
