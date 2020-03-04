package com.art1985.orderList.service.order;

import com.art1985.orderList.entities.Order;
import com.art1985.orderList.repository.OrderRepository;
import com.art1985.orderList.service.IService;
import com.art1985.orderList.service.order.exception.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements IService<Order> {
    private OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order create(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id " + id));
    }

    @Override
    public Order findByName(String name) {
        return orderRepository.findByName(name)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with name " + name));
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order deleteById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id " + id + " to delete"));
        orderRepository.deleteById(order.getId());
        return order;
    }

    @Override
    public Order deleteByName(String name) {
        Order order = orderRepository.findByName(name)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with name " + name + " to delete"));
        orderRepository.deleteById(order.getId());
        return order;
    }

    @Override
    public void update(Order order) {
        orderRepository.save(order);
    }

    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
