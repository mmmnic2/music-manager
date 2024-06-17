package com.firstversion.musicmanager.dto.response;

import com.firstversion.musicmanager.model.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterResponse {
    private String firstName;
    private String lastName;
    private String username;
    private Role role;
}
