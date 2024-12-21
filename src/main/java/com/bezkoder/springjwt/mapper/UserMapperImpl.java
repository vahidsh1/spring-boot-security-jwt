package com.bezkoder.springjwt.mapper;

import com.bezkoder.springjwt.entity.Role;
import com.bezkoder.springjwt.entity.UserEntity;
import com.bezkoder.springjwt.entity.UserEntity;
import com.bezkoder.springjwt.payload.UserDto;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toUserDto(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        Set<Role> roles = userEntity.getRoles().stream().collect(Collectors.toSet());
        return new UserDto(userEntity.getId(), userEntity.getUsername(), userEntity.getEmail(), userEntity.getPassword(), userEntity.isFirstLogin(),roles);
    }

}