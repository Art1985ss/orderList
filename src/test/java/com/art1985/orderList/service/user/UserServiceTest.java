package com.art1985.orderList.service.user;

import com.art1985.orderList.entities.EntityCreator;
import com.art1985.orderList.entities.User;
import com.art1985.orderList.repository.UserRepository;
import com.art1985.orderList.service.user.validation.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserValidationService userValidationService = mock(UserValidationService.class);
    private User user;
    private UserService victim;

    @BeforeEach
    void setUp() {
        victim = new UserService(userRepository, userValidationService);
        user = EntityCreator.createUser();
    }

    @Test
    void create() {
        when(userRepository.save(user)).thenReturn(user);
        assertEquals(user, victim.create(user));
        verify(userValidationService, times(1)).validate(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void findByIdFail() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());
        Exception exception = assertThrows(UserNotFoundException.class, () -> victim.findById(user.getId()));
        assertEquals("No user found with id " + user.getId(), exception.getMessage());
        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    void findById() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        assertEquals(user, victim.findById(user.getId()));
        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    void findByNameFail() {
        when(userRepository.findByLastName(user.getLastName())).thenReturn(Optional.empty());
        Exception exception = assertThrows(UserNotFoundException.class, () -> victim.findByName(user.getLastName()));
        assertEquals("No user found with last name " + user.getLastName(), exception.getMessage());
        verify(userRepository, times(1)).findByLastName(user.getLastName());
    }

    @Test
    void findByName() {
        when(userRepository.findByLastName(user.getLastName())).thenReturn(Optional.of(user));
        assertEquals(user, victim.findByName(user.getLastName()));
        verify(userRepository, times(1)).findByLastName(user.getLastName());
    }

    @Test
    void findAll() {
        List<User> userList = new ArrayList<>();
        userList.add(user);
        user = EntityCreator.createUser();
        user.setId(2L);
        user.setEmail("Test2@email");
        user.setLastName("TestLastName");
        userList.add(user);
        when(userRepository.findAll()).thenReturn(userList);
        assertEquals(userList, victim.findAll());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void deleteByIdFail() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());
        Exception exception = assertThrows(UserNotFoundException.class, () -> victim.deleteById(user.getId()));
        assertEquals("No user found with id " + user.getId() + " to delete", exception.getMessage());
        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    void deleteById() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        assertEquals(user, victim.deleteById(user.getId()));
        verify(userRepository, times(1)).findById(user.getId());
        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @Test
    void deleteByNameFail() {
        when(userRepository.findByLastName(user.getLastName())).thenReturn(Optional.empty());
        Exception exception = assertThrows(UserNotFoundException.class, () -> victim.deleteByName(user.getLastName()));
        assertEquals("No user found with name " + user.getLastName() + " to delete", exception.getMessage());
        verify(userRepository, times(1)).findByLastName(user.getLastName());
    }

    @Test
    void deleteByName() {
        when(userRepository.findByLastName(user.getLastName())).thenReturn(Optional.of(user));
        assertEquals(user, victim.deleteByName(user.getLastName()));
        verify(userRepository, times(1)).findByLastName(user.getLastName());
        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @Test
    void update() {
        when(userRepository.save(user)).thenReturn(user);
        user = EntityCreator.createUser();
        user.setVersion(2);
        victim.update(user);
        verify(userValidationService, times(1)).validate(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void findByEmailFail() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        Exception exception = assertThrows(UserNotFoundException.class, () -> victim.findByEmail(user.getEmail()));
        assertEquals("No user found with email " + user.getEmail(), exception.getMessage());
        verify(userRepository, times(1)).findByEmail(user.getEmail());
    }

    @Test
    public void findByEmail(){
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        assertEquals(user, victim.findByEmail(user.getEmail()));
        verify(userRepository, times(1)).findByEmail(user.getEmail());
    }
}