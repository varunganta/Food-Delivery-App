package com.example.demo.registration.token;

import com.example.demo.appuser.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }

    public boolean isTokenConfirmed(String token) {
        Optional<ConfirmationToken> optionalToken = confirmationTokenRepository.findByToken(token);
        return optionalToken.isPresent() && optionalToken.get().getConfirmedAt() != null;
    }

    public void deleteTokenByUser(AppUser existingUser) {
        confirmationTokenRepository.deleteByAppUser(existingUser);
    }
}
