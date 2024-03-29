package com.gkwc.simpleblog.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.Instant;

import javax.annotation.PostConstruct;

import com.gkwc.simpleblog.exception.SimpleBlogException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import static io.jsonwebtoken.Jwts.parser;
import static java.util.Date.from;

@Service
public class JwtProvider {
    private KeyStore keyStore;
    @Value("${jwt.keyStore.name}")
    private String keyStoreName;
    @Value("${jwt.keyStore.alias}")
    private String keyStoreAlias;
    @Value("${jwt.keyStore.password}")
    private String keyStorePassword;
    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;

    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream(keyStoreName);
            keyStore.load(resourceAsStream, keyStorePassword.toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
            throw new SimpleBlogException("Exception occured while loading keystore");
        }
    }

    public String generateToken(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        return generateTokenWithUsername(principal.getUsername());
    }

    public String generateTokenWithUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(from(Instant.now()))
                .signWith(getPrivateKey())
                .setExpiration(from(Instant.now().plusMillis(jwtExpirationInMillis)))
                .compact();
    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey(keyStoreAlias, keyStorePassword.toCharArray());
        } catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e) {
            throw new SimpleBlogException("Exception occured while retrieving private key from keystore");
        }
    }

    public boolean validateToken(String jwt) {
        parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
        return true;
    }

    private PublicKey getPublicKey() {
        try {
            return keyStore.getCertificate(keyStoreAlias).getPublicKey();
        } catch (KeyStoreException e) {
            throw new SimpleBlogException("Exception occured while retrieving public key from keystore");
        }
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = parser()
                .setSigningKey(getPublicKey())
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public Long getJwtExpirationInMillis() {
        return jwtExpirationInMillis;
    }
}
