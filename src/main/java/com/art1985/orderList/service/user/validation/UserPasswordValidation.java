package com.art1985.orderList.service.user.validation;

import com.art1985.orderList.entities.User;

public class UserPasswordValidation implements UserValidation {
    private static final byte MIN_LENGTH = 4;
    private static final byte MAX_LENGTH = 32;

    @Override
    public void validate(User user) {
        checkNull(user);
        String password = user.getPassword();
        checkPasswordNotNull(password);
        checkPasswordMinLength(password);
        checkPasswordMaxLength(password);
    }

    private void checkPasswordNotNull(String password) {
        if (password == null)
            throw new RuntimeException("User password should not be null!");
    }

    private void checkPasswordMinLength(String password) {
        if (password.length() < MIN_LENGTH)
            throw new RuntimeException("User password should be at least " + MIN_LENGTH + " characters long!");
    }

    private void checkPasswordMaxLength(String password) {
        if (password.length() > MAX_LENGTH)
            throw new RuntimeException("User password should not be longer than " + MAX_LENGTH + " characters long!");
    }
}
