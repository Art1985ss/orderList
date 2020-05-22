package com.art1985.orderList.web.dto;

import com.art1985.orderList.entities.EntityCreator;
import com.art1985.orderList.entities.Order;
import com.art1985.orderList.entities.Product;
import com.art1985.orderList.entities.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DtoConverterTest {
    @Test
    public void userConversion(){
        User user = EntityCreator.createUser();
        DtoUser dtoUser = DtoConverter.toDto(user);
        assertEquals(user, DtoConverter.fromDto(dtoUser));
    }

    @Test
    public void productConversion(){
        Product product = EntityCreator.createProduct();
        DtoProduct dtoProduct = DtoConverter.toDto(product);
        assertEquals(product, DtoConverter.fromDto(dtoProduct));
    }

    @Test
    public void orderConversion(){
        Order order = EntityCreator.createOrder();
        Product product = EntityCreator.createProduct();
        User user = EntityCreator.createUser();
        order.setUser(user);
        order.addProduct(product, 1);
        product = EntityCreator.createProduct();
        product.setId(2L);
        product.setName("TestProduct2");
        order.addProduct(product, 1);
        DtoOrder dtoOrder = DtoConverter.toDto(order);
        assertEquals(order, DtoConverter.fromDto(dtoOrder));
    }
}