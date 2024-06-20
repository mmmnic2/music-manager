package com.firstversion.musicmanager.service;

import com.firstversion.musicmanager.dto.request.AuthenticationRequest;
import com.firstversion.musicmanager.dto.request.RegisterRequest;
import com.firstversion.musicmanager.dto.response.AuthenticationResponse;
import com.firstversion.musicmanager.dto.response.RegisterResponse;
import com.firstversion.musicmanager.model.enums.Role;

public interface AuthenticationService {
    RegisterResponse register(RegisterRequest registerRequest, Role role);
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
    AuthenticationResponse refreshToken(String refreshToken);
}
