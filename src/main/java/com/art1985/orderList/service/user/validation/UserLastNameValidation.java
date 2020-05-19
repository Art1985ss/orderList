package com.art1985.orderList.service.user.validation;

import com.art1985.orderList.entities.User;

public class UserLastNameValidation implements UserValidation {
    private static final byte MIN_LENGTH = 4;
    private static final byte MAX_LENGTH = 32;

    @Override
    public void validate(User user) {
        checkNull(user);
        String LastName = user.getFirstName();
        checkNameNotNull(LastName);
        checkNameMinLength(LastName);
        checkNameMaxLength(LastName);
    }

    private void checkNameNotNull(String name) {
        if (name == null)
            throw new RuntimeException("User first name should not be null!");
    }

    private void checkNameMinLength(String name) {
        if (name.length() < MIN_LENGTH)
            throw new RuntimeException("User first name should be at least " + MIN_LENGTH + " characters long!");
    }

    private void checkNameMaxLength(String name) {
        if (name.length() > MAX_LENGTH)
            throw new RuntimeException("User first name should not be longer than " + MAX_LENGTH + " characters long!");
    }
}
