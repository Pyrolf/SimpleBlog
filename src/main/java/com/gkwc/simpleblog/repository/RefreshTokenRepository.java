package com.gkwc.simpleblog.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import com.gkwc.simpleblog.model.RefreshToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    @Transactional
    void deleteByToken(String token);
}
