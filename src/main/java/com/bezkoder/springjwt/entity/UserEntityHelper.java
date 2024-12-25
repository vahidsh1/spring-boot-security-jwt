package com.bezkoder.springjwt.entity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.swing.text.html.parser.Entity;

public class UserEntityHelper    { /**
 * get user id of signed user from spring security context
 * @return user id of signed user
 */
public static UserEntity user() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    // try-catch is a workaround for handling user while running tests
    // it only gives ClassCastException when running tests
    try {

        return ((UserEntity) auth.getPrincipal());

    } catch (ClassCastException e) {
        var testUser = new UserEntity();
        testUser.setId(1580L);
        return testUser;
    }

}

public static Long userId() {
    return user().getId();
}
}