package com.firstversion.musicmanager.service;

import com.firstversion.musicmanager.dto.request.RegisterRequest;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {
    void register(RegisterRequest userRequest);


}
