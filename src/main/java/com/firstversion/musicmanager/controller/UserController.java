package com.firstversion.musicmanager.controller;

import com.firstversion.musicmanager.dto.response.ResponseObject;
import com.firstversion.musicmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/say-hi")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok(new ResponseObject(HttpStatus.OK.value(), "success", "helloo"));
    }

}
