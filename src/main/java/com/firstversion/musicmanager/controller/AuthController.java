package com.firstversion.musicmanager.controller;

import com.firstversion.musicmanager.dto.request.RegisterRequest;
import com.firstversion.musicmanager.dto.request.AuthenticationRequest;
import com.firstversion.musicmanager.dto.response.ResponseObject;
import com.firstversion.musicmanager.dto.response.AuthenticationResponse;
import com.firstversion.musicmanager.dto.response.RegisterResponse;
import com.firstversion.musicmanager.exception.AlreadyExistException;
import com.firstversion.musicmanager.model.entity.Role;
import com.firstversion.musicmanager.service.AuthenticationService;
import com.firstversion.musicmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    UserService userService;
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        try {
            RegisterResponse response = authenticationService.register(registerRequest, Role.ROLE_USER);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(HttpStatus.OK.value(), "Register successfully", response));
        } catch (AlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ResponseObject(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }

    @PostMapping("/register/admin")
    public ResponseEntity<?> registerAdminAccount(@RequestBody RegisterRequest registerRequest) {
        try {
            RegisterResponse response = authenticationService.register(registerRequest, Role.ROLE_ADMIN);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(HttpStatus.OK.value(), "Register successfully", response));
        } catch (AlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ResponseObject(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            AuthenticationResponse response = authenticationService.authenticate(authenticationRequest);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String jwt) {
        try {
            AuthenticationResponse response = authenticationService.refreshToken(jwt.substring(7));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(HttpStatus.BAD_REQUEST.value(), "error: " + e.getMessage(), null)
            );
        }
    }


}
