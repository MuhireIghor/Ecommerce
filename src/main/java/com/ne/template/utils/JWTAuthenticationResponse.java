package com.ne.template.utils;

import com.ne.template.models.User;
import lombok.Data;

@Data
public class JWTAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
//    private User user;

    public JWTAuthenticationResponse(String accessToken ) {
        this.accessToken = accessToken;
//        this.user = user;
    }
}