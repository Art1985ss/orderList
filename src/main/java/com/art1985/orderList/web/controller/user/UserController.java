package com.art1985.orderList.web.controller.user;

import com.art1985.orderList.entities.User;
import com.art1985.orderList.service.user.UserService;
import com.art1985.orderList.web.controller.IController;
import com.art1985.orderList.web.dto.DtoConverter;
import com.art1985.orderList.web.dto.DtoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class UserController implements IController<DtoUser> {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<Void> create(DtoUser dtoUser) {
        User user = DtoConverter.fromDto(dtoUser);
        Long id = userService.create(user).getId();
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED).location(location).build();
    }

    @Override
    public ResponseEntity<List<DtoUser>> getAll() {
        List<User> users = userService.findAll();
        List<DtoUser> dtoUserList = users.stream().map(DtoConverter::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtoUserList);
    }

    @Override
    public ResponseEntity<DtoUser> findById(Long id) {
        User user = userService.findById(id);
        DtoUser dtoUser = DtoConverter.toDto(user);
        return ResponseEntity.ok(dtoUser);
    }

    @Override
    public ResponseEntity<DtoUser> findByName(String name) {
        User user = userService.findByName(name);
        DtoUser dtoUser = DtoConverter.toDto(user);
        return ResponseEntity.ok(dtoUser);
    }

    @Override
    public ResponseEntity<Void> deleteById(Long id) {
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Void> deleteById(String name) {
        userService.deleteByName(name);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Void> update(DtoUser dtoUser) {
        User user = DtoConverter.fromDto(dtoUser);
        userService.update(user);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
