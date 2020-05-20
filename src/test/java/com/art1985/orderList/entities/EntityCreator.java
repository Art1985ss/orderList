package com.art1985.orderList.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

public class EntityCreator {

    public static User createUser() {
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

    public static Order createOrder(){
        Order order = new Order();
        order.setId(1L);
        order.setName("Test");
        order.setUser(null);
        order.setProductListWithAmount(new HashMap<>());
        order.setVersion(1);
        order.setCreated(LocalDate.now().minusDays(10));
        order.setUpdated(LocalDate.now().minusDays(5));
        return order;
    }

    public static Product createProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test");
        product.setCategory(Category.FLOUR_PRODUCTS);
        product.setPrice(new BigDecimal("200"));
        product.setDiscount(new BigDecimal("10"));
        product.setVersion(1);
        product.setCreated(LocalDate.now().minusDays(10));
        product.setUpdated(LocalDate.now().minusDays(5));
        return product;
    }
}
