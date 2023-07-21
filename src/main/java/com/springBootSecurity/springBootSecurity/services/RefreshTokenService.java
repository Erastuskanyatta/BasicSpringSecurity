package com.springBootSecurity.springBootSecurity.services;


import com.springBootSecurity.springBootSecurity.model.RefreshToken;
import com.springBootSecurity.springBootSecurity.repo.RefreshTokenRepo;
import com.springBootSecurity.springBootSecurity.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
public class RefreshTokenService {
    @Autowired
    public RefreshTokenRepo refreshTokenRepo;
    @Autowired
    public UserRepo userRepo;
    public RefreshToken createRefreshToken(String username){
        RefreshToken refreshToken = RefreshToken.builder()
                .refreshToken(UUID.randomUUID().toString())
                .appUser(userRepo.findByUsername(username).get())
                .expiryTime(Instant.now().plusMillis(180000))
                .build();
        return refreshTokenRepo.save(refreshToken);
    }

    public Optional<RefreshToken> findByRefreshToken(String refreshToken) {
        return refreshTokenRepo.findByRefreshToken(refreshToken);
    }
// is the refresh token expired?
    public  RefreshToken verifyExpiry(RefreshToken refreshToken){
        if (refreshToken.getExpiryTime().compareTo(Instant.now())<0){
            refreshTokenRepo.delete(refreshToken);
            throw  new RuntimeException(refreshToken.getRefreshToken()  + " Expired.... ");
        }
        return refreshToken;
    }
}
