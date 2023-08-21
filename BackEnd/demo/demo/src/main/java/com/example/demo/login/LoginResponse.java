package com.example.demo.login;

public class LoginResponse {
    private boolean loginStatus;
    private String errorMessage;
    private final String jwt;
    private Long id;
    private String firstName;
    private String email;


    public LoginResponse(String jwt, String firstName, String email, Long id){
        this.jwt = jwt;
        this.firstName = firstName;
        this.email = email;
        this.id = id;
        this.loginStatus = true;
        this.errorMessage = "";
    }

//    public LoginResponse(String token, boolean loginStatus, String errorMessage, String jwt) {
//        this.token = token;
//        this.loginStatus = loginStatus;
//        this.errorMessage = errorMessage;
//        this.jwt = jwt;
//    }

    public String getJwt() {
        return jwt;
    }

    public boolean isLoginStatus() {
        return loginStatus;
    }



    public void setLoginStatus(boolean loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
