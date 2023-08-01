package com.example.demo.registration;

public class RegistrationResponse {
    private String token;
    private boolean verificationStatus;

    public RegistrationResponse(String token, boolean verificationStatus) {
        this.token = token;
        this.verificationStatus = verificationStatus;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(boolean verificationStatus) {
        this.verificationStatus = verificationStatus;
    }
    // add message responses here and remove from frontend
}
