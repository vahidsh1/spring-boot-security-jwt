package com.bezkoder.springjwt.mapper;

import com.bezkoder.springjwt.entity.UserEntity;
import com.bezkoder.springjwt.entity.UserEntity;
import com.bezkoder.springjwt.payload.UserDto;

public interface UserMapper {

    UserDto toUserDto(UserEntity userEntity);
}