package com.bezkoder.springjwt.mapper;

import com.bezkoder.springjwt.entity.User;
import com.bezkoder.springjwt.payload.UserDto;

public interface UserMapper {

    UserDto toUserDto(User user);
}