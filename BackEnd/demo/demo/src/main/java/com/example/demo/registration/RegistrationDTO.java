package com.example.demo.registration;

import com.example.demo.appuser.AppUser;
import com.example.demo.appuser.AppUserRole;

public class RegistrationDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean verified;
    private AppUserRole appUserRole;

    private String restaurantName;

    private String restaurantLocation;

    public RegistrationDTO() {

    }

    public RegistrationDTO(String firstName, String lastName, String email, String password, boolean verified, AppUserRole appUserRole, String restaurantName, String restaurantLocation) {
        // add message field
        // add success - whether login/registration succeeded or not
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.verified = verified;
        this.appUserRole = appUserRole;
        this.restaurantName = restaurantName;
        this.restaurantLocation = restaurantLocation;
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

    public AppUserRole getAppUserRole() {
        return appUserRole;
    }

    public void setAppUserRole(AppUserRole appUserRole) {
        this.appUserRole = appUserRole;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantLocation() {
        return restaurantLocation;
    }

    public void setRestaurantLocation(String restaurantLocation) {
        this.restaurantLocation = restaurantLocation;
    }
}
