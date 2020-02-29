package com.art1985.orderList.service.user;

import com.art1985.orderList.entities.User;
import com.art1985.orderList.repository.UserRepository;
import com.art1985.orderList.service.IService;
import com.art1985.orderList.service.user.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IService<User> {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No user found with id " + id));
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByName(name)
                .orElseThrow(() -> new UserNotFoundException("No user found with name " + name));
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
        User user = userRepository.findByName(name)
                .orElseThrow(() -> new UserNotFoundException("No user found with name " + name + " to delete"));
        userRepository.deleteById(user.getId());
        return user;
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }
}
