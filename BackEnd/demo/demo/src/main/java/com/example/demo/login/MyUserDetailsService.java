//package com.example.demo.login;
//
//import com.example.demo.appuser.AppUser;
//import com.example.demo.appuser.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//
//@Service
//public class MyUserDetailsService implements UserDetailsService {
//    @Autowired
//    UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        AppUser appUser = userRepository.findUserByEmail(email);
//        if (appUser == null) {
//            throw new UsernameNotFoundException("email not found " + email);
//        }
//        return new User(appUser.getEmail(), appUser.getPassword(), new ArrayList<>());
//    }
//}
