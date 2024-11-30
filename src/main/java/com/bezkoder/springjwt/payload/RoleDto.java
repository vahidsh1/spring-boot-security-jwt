package com.bezkoder.springjwt.payload;

import com.bezkoder.springjwt.entity.ERole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {

    private Integer id;

    private ERole name;


}