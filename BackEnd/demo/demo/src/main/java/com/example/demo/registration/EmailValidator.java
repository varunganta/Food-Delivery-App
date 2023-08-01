package com.example.demo.registration;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Component
public class EmailValidator implements Predicate<String> {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    @Override
    public boolean test(String email) {
        return email.matches(EMAIL_REGEX);
    }
}
