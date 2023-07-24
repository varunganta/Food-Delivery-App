package com.example.demo.login;

import com.example.demo.Jwt.JwtUtil;
import com.example.demo.appuser.AppUser;
import com.example.demo.appuser.AppUserService;
import com.example.demo.appuser.UserRepository;
import com.example.demo.security.config.LoginWebSecurityConfig;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin("http://localhost:3000")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AppUserService userDetailsService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final AppUserService appUserService;



    public LoginController(AppUserService userDetailsService, AppUserService appUserService, JwtUtil jwtUtil, UserRepository userRepository) {
        this.userDetailsService = userDetailsService;
        this.appUserService = appUserService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);

        AppUser appUser = userRepository.findUserByEmail(loginRequest.getEmail());

        if (appUser == null) {
            LoginResponse loginResponse = new LoginResponse(null, null, null, 0);
            loginResponse.setLoginStatus(false);
            loginResponse.setErrorMessage("Invalid username or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginResponse);
        }

        if (!appUser.isEnabled()) {
            LoginResponse loginResponse = new LoginResponse(null, null, null, 0);
            loginResponse.setLoginStatus(false);
            loginResponse.setErrorMessage("Account is disabled");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginResponse);
        }

        String firstName = appUser.getFirstName();
        String email = appUser.getEmail();
        int id = appUser.getId();

        LoginResponse loginResponse = new LoginResponse(jwt, firstName, email, id);
        return ResponseEntity.ok(loginResponse);
    }
}
