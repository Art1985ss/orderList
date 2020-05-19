package com.art1985.orderList.service.user;

import com.art1985.orderList.entities.User;
import com.art1985.orderList.repository.UserRepository;
import com.art1985.orderList.service.user.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserValidationServiceTest {
    private static final byte MIN_LENGTH = 4;
    private static final byte MAX_LENGTH = 32;
    private UserRepository userRepository = mock(UserRepository.class);
    private UserValidationService victim;
    private Set<UserValidation> validations = new HashSet<>();
    private User testUser;

    @BeforeEach
    void setUp() {
        validations.add(new UserFirstNameValidation());
        validations.add(new UserLastNameValidation());
        validations.add(new UserPasswordValidation());
        validations.add(new UserEmailValidation(userRepository));
        victim = new UserValidationService(validations);
        testUser = createUser();
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
    }

    @Test
    void objectNull() {
        testUser = null;
        Exception exception = assertThrows(RuntimeException.class, () -> victim.validate(testUser));
        assertEquals("User object should not be null!", exception.getMessage());
    }

    @Test
    void firstNameNull() {
        testUser.setFirstName(null);
        Exception exception = assertThrows(RuntimeException.class, () -> victim.validate(testUser));
        assertEquals("User first name should not be null!", exception.getMessage());
    }

    @Test
    void firstNameTooShort() {
        testUser.setFirstName("a");
        Exception exception = assertThrows(RuntimeException.class, () -> victim.validate(testUser));
        assertEquals("User first name should be at least " + MIN_LENGTH + " characters long!", exception.getMessage());
    }

    @Test
    void firstNameTooLong() {
        testUser.setFirstName("this name is so long and so hard to write that user who came out with this idea should go to psychiatric ward");
        Exception exception = assertThrows(RuntimeException.class, () -> victim.validate(testUser));
        assertEquals("User first name should not be longer than " + MAX_LENGTH + " characters long!", exception.getMessage());
    }

    @Test
    void lastNameNull() {
        testUser.setLastName(null);
        Exception exception = assertThrows(RuntimeException.class, () -> victim.validate(testUser));
        assertEquals("User last name should not be null!", exception.getMessage());
    }

    @Test
    void lastNameTooShort() {
        testUser.setLastName("a");
        Exception exception = assertThrows(RuntimeException.class, () -> victim.validate(testUser));
        assertEquals("User last name should be at least " + MIN_LENGTH + " characters long!", exception.getMessage());
    }

    @Test
    void lastNameTooLong() {
        testUser.setLastName("this name is so long and so hard to write that user who came out with this idea should go to psychiatric ward");
        Exception exception = assertThrows(RuntimeException.class, () -> victim.validate(testUser));
        assertEquals("User last name should not be longer than " + MAX_LENGTH + " characters long!", exception.getMessage());
    }

    @Test
    void emailNull() {
        testUser.setEmail(null);
        Exception exception = assertThrows(RuntimeException.class, () -> victim.validate(testUser));
        assertEquals("User should have email!", exception.getMessage());
    }

    @Test
    void emailNotUnique() {
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser));
        Exception exception = assertThrows(RuntimeException.class, () -> victim.validate(testUser));
        assertEquals("User with such email already exists!", exception.getMessage());
    }

    @Test
    void passwordNull() {
        testUser.setPassword(null);
        Exception exception = assertThrows(RuntimeException.class, () -> victim.validate(testUser));
        assertEquals("User password should not be null!", exception.getMessage());
    }

    @Test
    void passwordTooShort() {
        testUser.setPassword("a");
        Exception exception = assertThrows(RuntimeException.class, () -> victim.validate(testUser));
        assertEquals("User password should be at least " + MIN_LENGTH + " characters long!", exception.getMessage());
    }

    @Test
    void passwordTooLong() {
        testUser.setPassword("try to remember this password for a long time, good luck with that");
        Exception exception = assertThrows(RuntimeException.class, () -> victim.validate(testUser));
        assertEquals("User password should not be longer than " + MAX_LENGTH + " characters long!", exception.getMessage());
    }

    @Test
    void shouldPassValidation() {
        victim.validate(testUser);
    }


    private User createUser() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("TestFirst");
        user.setLastName("TestLast");
        user.setAddress("TestAddress");
        user.setEmail("Test@Email");
        user.setPassword("TestPassword");
        user.setVersion(1);
        user.setCreated(LocalDate.now().minusDays(10));
        user.setUpdated(LocalDate.now().minusDays(5));
        return user;
    }
}