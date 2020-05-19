package com.art1985.orderList.service.user.validation;

import com.art1985.orderList.entities.User;
import com.art1985.orderList.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserEmailValidation implements UserValidation {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void validate(User user) {
        checkNull(user);
        String email = user.getEmail();
        checkEmailNotNull(email);
        checkIfExists(email);
    }

    private void checkEmailNotNull(String email) {
        if (email == null)
            throw new RuntimeException("User should have email!");
    }

    private void checkIfExists(String email) {
        if (userRepository.findByEmail(email).isPresent())
            throw new RuntimeException("User with such email already exists!");
    }
}
