package com.example.demo.appuser;

import com.example.demo.login.LoginRequest;
import com.example.demo.login.LoginResponse;
import com.example.demo.registration.token.ConfirmationToken;
import com.example.demo.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "User with email %s not found";

    @Autowired
    private UserRepository userRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

//    @Autowired
//    PasswordEncoder passwordEncoder;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        AppUser appUser = userRepository.findUserByEmail(email);
//        if (appUser == null) {
//            throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email));
//        }
//        return new User(appUser.getEmail(), appUser.getPassword(), new ArrayList<>());
//    }
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(AppUser appUser) {
        System.out.println("here");
        boolean userExists = userRepository
                .findByEmail(appUser.getEmail())
                .isPresent();
        System.out.println("here2");
        if (userExists) {
            throw new IllegalStateException("email already in use");
        }

        String encodedPassword = bCryptPasswordEncoder
                .encode(appUser.getPassword());
//
//        String encodedPassword = passwordEncoder
//                .encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);

        userRepository.save(appUser);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );

        confirmationTokenService.saveConfirmationToken(
                confirmationToken);

    //        TODO: SEND EMAIL

        return token;
    }
    public int enableAppUser(String email) {
        return userRepository.enableAppUser(email);
    }

    public AppUser findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
}
