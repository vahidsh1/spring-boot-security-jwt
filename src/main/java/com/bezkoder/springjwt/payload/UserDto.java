package com.bezkoder.springjwt.payload;

import com.bezkoder.springjwt.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;




public record UserDto(Long id, String username, String email,String password, boolean isFirstLogin, Set<Role> roles) {

}


