package com.gkwc.simpleblog.service;

import java.time.Instant;
import java.util.Optional;

import com.gkwc.simpleblog.dto.AuthenticationResponse;
import com.gkwc.simpleblog.dto.LoginRequest;
import com.gkwc.simpleblog.dto.RefreshTokenRequest;
import com.gkwc.simpleblog.dto.RegisterRequest;
import com.gkwc.simpleblog.exception.EmailTakenException;
import com.gkwc.simpleblog.exception.UsernameNotFoundException;
import com.gkwc.simpleblog.exception.UsernameTakenException;
import com.gkwc.simpleblog.exception.PasswordIncorrectException;
import com.gkwc.simpleblog.model.User;
import com.gkwc.simpleblog.repository.UserRepository;
import com.gkwc.simpleblog.security.JwtProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private RefreshTokenService refreshTokenService;

    public void signup(RegisterRequest registerRequest) {
        if (userRepository.findByUserName(registerRequest.getUsername()).isPresent()) {
            throw new UsernameTakenException("Username Taken");
        }
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new EmailTakenException("Email Taken");
        }
        User user = new User();
        user.setUserName(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodePassword(registerRequest.getPassword()));

        userRepository.save(user);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByUserName(loginRequest.getUsername());
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("Username Not Found");
        } else if(user.get().getPassword().equals(loginRequest.getPassword())) {
            throw new PasswordIncorrectException("Wrong Password");
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), 
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername())
                .build();
    }

    public Optional<org.springframework.security.core.userdetails.User> getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Optional.of(principal);
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUsername(refreshTokenRequest.getUsername());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }
}
