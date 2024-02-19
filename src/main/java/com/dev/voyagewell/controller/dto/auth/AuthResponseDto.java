package com.dev.voyagewell.controller.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponseDto {
    private String accessToken;
    private String tokenType = "Bearer ";
    private String avatarUrl;
    private String nickName;

    public AuthResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
