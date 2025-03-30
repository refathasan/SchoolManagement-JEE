package com.refathasan.school_management.dto;

public class AuthenticationResponse {
    private String jwtToken;
    public AuthenticationResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }
    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
