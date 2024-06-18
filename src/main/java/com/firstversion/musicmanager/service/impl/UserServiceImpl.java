package com.firstversion.musicmanager.service.impl;

import com.firstversion.musicmanager.dto.request.RegisterRequest;
import com.firstversion.musicmanager.exception.NotFoundException;
import com.firstversion.musicmanager.model.entity.User;
import com.firstversion.musicmanager.repository.UserRepository;
import com.firstversion.musicmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public void register(RegisterRequest userRequest) {

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new NotFoundException("User not found with username + " + username));
        return user;
    }
}
