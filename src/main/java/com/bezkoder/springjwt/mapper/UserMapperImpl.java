package com.bezkoder.springjwt.mapper;

import com.bezkoder.springjwt.entity.Role;
import com.bezkoder.springjwt.entity.User;
import com.bezkoder.springjwt.payload.UserDto;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        }
        Set<Role> roles = user.getRoles().stream().collect(Collectors.toSet());
        return new UserDto(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), user.isFirstLogin(),roles);
    }

}