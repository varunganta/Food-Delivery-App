package com.example.demo.registration;

public class RegistrationDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean verified;

    public RegistrationDTO() {

    }

    public RegistrationDTO(String firstName, String lastName, String email, String password, boolean verified) {
        // add message field
        // add success - whether login/registration succeeded or not
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.verified = verified;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
