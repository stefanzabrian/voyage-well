package com.dev.voyagewell.controller.dto.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UserProfileDto {
    @NotNull(message = "first name must not be null")
    @NotBlank(message = "first name must not be blank")
    private String firstName;
    @NotNull(message = "last name must not be null")
    @NotBlank(message = "last name must not be blank")
    private String lastName;
    @NotNull(message = "nick name must not be null")
    @NotBlank(message = "nick name must not be blank")
    private String nickName;
    @Email(message = "Email must be a well-formed email address")
    @NotNull(message = "Email must not be null")
    @NotBlank(message = "Email must not be blank")
    private String email;
    @NotNull(message = "bio info must not be null")
    @NotBlank(message = "bio info must not be blank")
    private String bioInfo;
    @NotNull(message = "phone number must not be null")
    @NotBlank(message = "phone number must not be blank")
    private String phoneNumber;
    @NotNull(message = "avatar url must not be null")
    @NotBlank(message = "avatar url must not be blank")
    private String avatarUrl;

    @Override
    public String toString() {
        return "UserProfileDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                ", bioInfo='" + bioInfo + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}
