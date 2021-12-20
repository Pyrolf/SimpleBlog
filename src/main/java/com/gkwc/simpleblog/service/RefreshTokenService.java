package com.gkwc.simpleblog.service;

import java.time.Instant;
import java.util.UUID;

import javax.transaction.Transactional;

import com.gkwc.simpleblog.exception.SimpleBlogException;
import com.gkwc.simpleblog.model.RefreshToken;
import com.gkwc.simpleblog.repository.RefreshTokenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedOn(Instant.now());

        return refreshTokenRepository.save(refreshToken);
    }

    void validateRefreshToken(String token) {
        refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new SimpleBlogException("Invalid refresh Token"));
    }

    @Transactional
    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}
