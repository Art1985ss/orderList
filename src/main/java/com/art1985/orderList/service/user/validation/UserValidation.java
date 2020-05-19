package com.art1985.orderList.service.user.validation;

import com.art1985.orderList.entities.User;

import java.util.NoSuchElementException;

public interface UserValidation {

    void validate(User user);

    default void checkNull(User user) {
        if (user == null)
            throw new RuntimeException("User object should not be null!");
    }
}
