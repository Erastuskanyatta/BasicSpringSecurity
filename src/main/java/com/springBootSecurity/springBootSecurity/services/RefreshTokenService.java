package com.springBootSecurity.springBootSecurity.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.springBootSecurity.springBootSecurity.model.AppUser;
import com.springBootSecurity.springBootSecurity.model.RefreshToken;
import com.springBootSecurity.springBootSecurity.repo.RefreshTokenRepo;
import com.springBootSecurity.springBootSecurity.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
public class RefreshTokenService {
    Algorithm algorithm = Algorithm.HMAC256("SecretKey".getBytes());

    @Autowired
    public RefreshTokenRepo refreshTokenRepo;
    @Autowired
    public UserRepo userRepo;

    public RefreshToken createRefreshToken(String username){
        RefreshToken refreshToken = RefreshToken.builder()
                .refreshToken(generateRefreshToken())
                .appUser(userRepo.findByUsername(username).get())
                .expiryTime(Instant.now().plusMillis(259200000)) //72 hrs
                .build();
        return refreshTokenRepo.save(refreshToken);
    }

    String generateRefreshToken() {
       AppUser appUser = new AppUser();
        JWTCreator.Builder jwtBuilder = JWT.create()
                .withSubject(appUser.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 259200000))
                .withIssuedAt(new Date(System.currentTimeMillis()));
        return jwtBuilder.sign(algorithm);
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
