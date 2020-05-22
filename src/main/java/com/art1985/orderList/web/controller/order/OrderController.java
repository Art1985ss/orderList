package com.art1985.orderList.web.controller.order;

import com.art1985.orderList.entities.Order;
import com.art1985.orderList.service.order.OrderService;
import com.art1985.orderList.web.controller.IController;
import com.art1985.orderList.web.dto.DtoConverter;
import com.art1985.orderList.web.dto.DtoOrder;
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
@RequestMapping("/api/v1/orders")
public class OrderController implements IController<DtoOrder> {
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public ResponseEntity<Void> create(DtoOrder dtoOrder) {
        Order order = DtoConverter.fromDto(dtoOrder);
        Long id = orderService.create(order).getId();
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED).location(location).build();
    }

    @Override
    public ResponseEntity<List<DtoOrder>> getAll() {
        List<Order> orders = orderService.findAll();
        List<DtoOrder> dtoOrderList = orders.stream().map(DtoConverter::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtoOrderList);
    }

    @Override
    public ResponseEntity<DtoOrder> findById(Long id) {
        Order order = orderService.findById(id);
        DtoOrder dtoOrder = DtoConverter.toDto(order);
        return ResponseEntity.ok(dtoOrder);
    }

    @Override
    public ResponseEntity<DtoOrder> findByName(String name) {
        Order order = orderService.findByName(name);
        DtoOrder dtoOrder = DtoConverter.toDto(order);
        return ResponseEntity.ok(dtoOrder);
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
    public ResponseEntity<Void> update(DtoOrder dtoOrder) {
        orderService.update(DtoConverter.fromDto(dtoOrder));
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
