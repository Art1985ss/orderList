package com.art1985.orderList.service.order;

import com.art1985.orderList.entities.EntityCreator;
import com.art1985.orderList.entities.Order;
import com.art1985.orderList.entities.User;
import com.art1985.orderList.repository.OrderRepository;
import com.art1985.orderList.service.order.exception.OrderNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {
    private final OrderRepository orderRepository = mock(OrderRepository.class);
    private Order order;
    private OrderService victim;

    @BeforeEach
    void setUp() {
        victim = new OrderService(orderRepository);
        order = EntityCreator.createOrder();
    }

    @Test
    void create() {
        when(orderRepository.save(order)).thenReturn(order);
        assertEquals(order, victim.create(order));
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void findByIdFail() {
        when(orderRepository.findById(order.getId())).thenReturn(Optional.empty());
        Exception exception = assertThrows(OrderNotFoundException.class, () -> victim.findById(order.getId()));
        assertEquals("No order found with id " + order.getId(), exception.getMessage());
        verify(orderRepository, times(1)).findById(order.getId());
    }

    @Test
    void findById() {
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        assertEquals(order, victim.findById(order.getId()));
        verify(orderRepository, times(1)).findById(order.getId());
    }

    @Test
    void findByNameFail() {
        when(orderRepository.findByName(order.getName())).thenReturn(Optional.empty());
        Exception exception = assertThrows(OrderNotFoundException.class, () -> victim.findByName(order.getName()));
        assertEquals("No order found with name " + order.getName(), exception.getMessage());
        verify(orderRepository, times(1)).findByName(order.getName());
    }

    @Test
    void findByName() {
        when(orderRepository.findByName(order.getName())).thenReturn(Optional.of(order));
        assertEquals(order, victim.findByName(order.getName()));
        verify(orderRepository, times(1)).findByName(order.getName());
    }

    @Test
    void findAll() {
        List<Order> orderList = new ArrayList<>();
        orderList.add(order);
        order = EntityCreator.createOrder();
        order.setId(2L);
        order.setName("TestName2");
        orderList.add(order);
        when(orderRepository.findAll()).thenReturn(orderList);
        assertEquals(orderList, victim.findAll());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void deleteByIdFail() {
        when(orderRepository.findById(order.getId())).thenReturn(Optional.empty());
        Exception exception = assertThrows(OrderNotFoundException.class, () -> victim.deleteById(order.getId()));
        assertEquals("No order found with id " + order.getId() + " to delete", exception.getMessage());
        verify(orderRepository, times(1)).findById(order.getId());
    }

    @Test
    void deleteById() {
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        assertEquals(order, victim.deleteById(order.getId()));
        verify(orderRepository, times(1)).findById(order.getId());
        verify(orderRepository, times(1)).deleteById(order.getId());
    }

    @Test
    void deleteByNameFail() {
        when(orderRepository.findByName(order.getName())).thenReturn(Optional.empty());
        Exception exception = assertThrows(OrderNotFoundException.class, () -> victim.deleteByName(order.getName()));
        assertEquals("No order found with name " + order.getName() + " to delete", exception.getMessage());
        verify(orderRepository, times(1)).findByName(order.getName());
    }

    @Test
    void deleteByName() {
        when(orderRepository.findByName(order.getName())).thenReturn(Optional.of(order));
        assertEquals(order, victim.deleteByName(order.getName()));
        verify(orderRepository, times(1)).findByName(order.getName());
        verify(orderRepository, times(1)).deleteById(order.getId());
    }

    @Test
    void update() {
        when(orderRepository.save(order)).thenReturn(order);
        order = EntityCreator.createOrder();
        order.setVersion(2);
        victim.update(order);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void findByUser() {
        User user = EntityCreator.createUser();
        order.setUser(user);
        List<Order> orderList = new ArrayList<>();
        orderList.add(order);
        when(orderRepository.findByUserId(user.getId())).thenReturn(orderList);
        assertEquals(orderList, victim.findByUserId(user.getId()));
        verify(orderRepository, times(1)).findByUserId(user.getId());
    }
}