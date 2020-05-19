package com.art1985.orderList.service.user;

import com.art1985.orderList.entities.User;
import com.art1985.orderList.service.user.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserValidationService {
    private Set<UserValidation> validations;

    @Autowired
    public UserValidationService(Set<UserValidation> validations) {
        this.validations = validations;
    }

    public void validate(User user) {
        validations.forEach(v -> v.validate(user));
    }
}
