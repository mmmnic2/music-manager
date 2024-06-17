package com.firstversion.musicmanager.service.impl;

import com.firstversion.musicmanager.dto.request.AuthenticationRequest;
import com.firstversion.musicmanager.dto.request.RegisterRequest;
import com.firstversion.musicmanager.dto.response.AuthenticationResponse;
import com.firstversion.musicmanager.dto.response.RegisterResponse;
import com.firstversion.musicmanager.exception.AlreadyExistException;
import com.firstversion.musicmanager.exception.NotFoundException;
import com.firstversion.musicmanager.exception.UserNotAuthorizedException;
import com.firstversion.musicmanager.model.entity.Role;
import com.firstversion.musicmanager.model.entity.User;
import com.firstversion.musicmanager.repository.UserRepository;
import com.firstversion.musicmanager.security.jwt.JwtService;
import com.firstversion.musicmanager.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    JwtService jwtService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;

    // dùng chung cho cả role user và admin
    @Override
    public RegisterResponse register(RegisterRequest registerRequest, Role role) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new AlreadyExistException("Username has already existed.");
        }
        User user = new User();
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setUsername(registerRequest.getUsername());
        user.setLastName(registerRequest.getLastName());
        user.setFirstName(registerRequest.getFirstName());
        User savedUser = userRepository.save(user);
        return toRegisterResponse(savedUser);
    }

    private RegisterResponse toRegisterResponse(User user) {
        return RegisterResponse.builder()
                .role(Role.valueOf(user.getRole().name()))
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);
        return new AuthenticationResponse(token, refreshToken, jwtService.getJwtExpirationTime());
    }

    @Override
    public AuthenticationResponse refreshToken(String refreshToken) {
        if (jwtService.validateToken(refreshToken)) {
            String username = jwtService.extractUsername(refreshToken);
            User existingUser = userRepository.findUserByUsername(username).orElseThrow(() -> new NotFoundException("Username not found."));
            if (jwtService.isTokenValid(refreshToken, existingUser)) {
                final String accessToken = jwtService.generateToken(existingUser);
                AuthenticationResponse response = new AuthenticationResponse(accessToken, refreshToken, jwtService.getJwtExpirationTime());
                return response;
            } else throw new UserNotAuthorizedException("Token is not valid");
        }
        return null;
    }
}
