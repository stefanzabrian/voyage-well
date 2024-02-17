package com.dev.voyagewell.controller.dto.register;

import com.dev.voyagewell.model.user.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
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
    private boolean isTermsAndConditions ;
    @JsonProperty
    @NotNull
    private boolean isPrivacyPolicy;

    private List<Role> roles;

    @Override
    public String toString() {
        return "RegisterDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", isTermsAndConditions=" + isTermsAndConditions +
                ", isPrivacyPolicy=" + isPrivacyPolicy +
                ", roles=" + roles +
                '}';
    }
}
