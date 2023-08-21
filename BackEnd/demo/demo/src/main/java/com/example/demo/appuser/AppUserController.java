package com.example.demo.appuser;

import com.example.demo.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/appUser")
@CrossOrigin("*")
public class AppUserController {
    private final AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/role")
    public ResponseEntity<?> getUserRoleByEmail(@RequestParam String email) {
        try {
            System.out.println("Received request with email: " + email);
            String role = appUserService.getUserRoleByEmail(email);
            return ResponseEntity.ok(Map.of("role", role));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
