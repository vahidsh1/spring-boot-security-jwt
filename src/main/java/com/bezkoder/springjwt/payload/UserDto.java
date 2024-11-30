package com.bezkoder.springjwt.payload;

import com.bezkoder.springjwt.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String password;
    private boolean isFirstLogin;
    private Set<Role> roles = new HashSet<>();
    public UserDto(String username, String email, String password, boolean isFirstLogin, Set<Role> roles) {
        this.username = username;this.email = email;
        this.password = password;
        this.isFirstLogin = isFirstLogin;
        this.roles = roles;
    }
}


