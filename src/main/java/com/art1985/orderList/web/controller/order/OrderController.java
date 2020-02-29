package com.art1985.orderList.web.controller.order;

import com.art1985.orderList.entities.Order;
import com.art1985.orderList.service.order.OrderService;
import com.art1985.orderList.web.controller.IController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController implements IController<Order> {
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public ResponseEntity<Void> create(Order order) {
        Long id = orderService.create(order).getId();
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED).location(location).build();
    }

    @Override
    public ResponseEntity<List<Order>> getAll() {
        List<Order> orders = orderService.findAll();
        return ResponseEntity.ok(orders);
    }

    @Override
    public ResponseEntity<Order> findById(Long id) {
        Order order = orderService.findById(id);
        return ResponseEntity.ok(order);
    }

    @Override
    public ResponseEntity<Order> findByName(String name) {
        Order order = orderService.findByName(name);
        return ResponseEntity.ok(order);
    }

    @Override
    public ResponseEntity<Void> deleteById(Long id) {
        orderService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Void> deleteById(String name) {
        orderService.deleteByName(name);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Void> update(Order order) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
