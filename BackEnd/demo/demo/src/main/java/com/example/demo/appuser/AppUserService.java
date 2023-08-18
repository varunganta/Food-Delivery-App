package com.example.demo.appuser;

import com.example.demo.customer.CustomerRepository;
import com.example.demo.login.LoginRequest;
import com.example.demo.login.LoginResponse;
import com.example.demo.registration.RegistrationDTO;
import com.example.demo.registration.token.ConfirmationToken;
import com.example.demo.registration.token.ConfirmationTokenService;
import com.example.demo.restaurant.Restaurant;
import com.example.demo.restaurant.RestaurantRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "User with email %s not found";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CustomerRepository customerRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(USER_NOT_FOUND_MSG, email)));
    }

    public List<AppUser> getAllAppUsers() {
        return userRepository.findAll();
    }

    public AppUserRole getAppUserRole(Long userId) {
        AppUser appUser = userRepository.findById(userId).orElse(null);
        if (appUser != null) {
            return appUser.getAppUserRole();
        }
        return null;
    }

    public AppUser getAppUserById(Long userId) {
//        int uId = userId.intValue();
        return userRepository.findById(userId).orElse(null);
    }

    public String signUpUser(Restaurant restaurant) {
        boolean userExists = userRepository
                .findByEmail(restaurant.getAppUser().getEmail())
                .isPresent();
        if (userExists) {
            throw new IllegalStateException("email already in use");
        }

        String encodedPassword = bCryptPasswordEncoder
                .encode(restaurant.getAppUser().getPassword());

        restaurant.getAppUser().setPassword(encodedPassword);


        userRepository.save(restaurant.getAppUser());
        restaurantRepository.save(restaurant);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                restaurant.getAppUser()
        );

        confirmationTokenService.saveConfirmationToken(
                confirmationToken);

    //        TODO: SEND EMAIL

        return token;
    }
//    public String signUpUser(RegistrationDTO registrationDTO) {
//        boolean userExists = userRepository
//                .findByEmail(registrationDTO.getEmail())
//                .isPresent();
//        if (userExists) {
//            throw new IllegalStateException("email already in use");
//        }
//
//        String encodedPassword = bCryptPasswordEncoder
//                .encode(registrationDTO.getPassword());
//
//        AppUser appUser = new AppUser();
//        appUser.setFirstName(registrationDTO.getFirstName());
//        appUser.setLastName(registrationDTO.getLastName());
//        appUser.setEmail(registrationDTO.getEmail());
//        appUser.setPassword(encodedPassword);
//        appUser.setAppUserRole(registrationDTO.getAppUserRole());
//
//        userRepository.save(appUser);
//
//        String token = UUID.randomUUID().toString();
//
//        ConfirmationToken confirmationToken = new ConfirmationToken(
//                token,
//                LocalDateTime.now(),
//                LocalDateTime.now().plusMinutes(15),
//                appUser
//        );
//
//        confirmationTokenService.saveConfirmationToken(confirmationToken);
//
//        // TODO: SEND EMAIL
//
//        return token;
//    }
    public int enableAppUser(String email) {
        return userRepository.enableAppUser(email);
    }

    public AppUser findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public AppUser updateAppUser(AppUser existingCustomer) {
        return userRepository.save(existingCustomer);
    }

    public void deleteAppUser(AppUser existingCustomer) {
        userRepository.delete(existingCustomer);
    }

    public String getUserRoleByEmail(String email) {
        Optional<AppUser> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            AppUser user = userOptional.get();
            String role = String.valueOf(user.getAppUserRole());
            return role;
        } else {
            throw new RuntimeException("User not found with the provided email");
        }
    }
}
