package com.art1985.orderList.service.user;

import com.art1985.orderList.entities.User;
import com.art1985.orderList.repository.UserRepository;
import com.art1985.orderList.service.IService;
import com.art1985.orderList.service.user.validation.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IService<User> {
    private final UserRepository userRepository;
    private final UserValidationService userValidationService;

    @Autowired
    public UserService(UserRepository userRepository, UserValidationService userValidationService) {
        this.userRepository = userRepository;
        this.userValidationService = userValidationService;
    }

    @Override
    public User create(User user) {
        userValidationService.validate(user);
        return userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No user found with id " + id));
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByLastName(name)
                .orElseThrow(() -> new UserNotFoundException("No user found with last name " + name));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User deleteById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No user found with id " + id + " to delete"));
        userRepository.deleteById(user.getId());
        return user;
    }

    @Override
    public User deleteByName(String name) {
        User user = userRepository.findByLastName(name)
                .orElseThrow(() -> new UserNotFoundException("No user found with name " + name + " to delete"));
        userRepository.deleteById(user.getId());
        return user;
    }

    @Override
    public void update(User user) {
        user.setVersion(user.getVersion() + 1);
        userValidationService.validate(user);
        userRepository.save(user);
    }
}
