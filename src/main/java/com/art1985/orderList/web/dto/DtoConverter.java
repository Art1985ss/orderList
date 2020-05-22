package com.art1985.orderList.web.dto;

import com.art1985.orderList.entities.Order;
import com.art1985.orderList.entities.Product;
import com.art1985.orderList.entities.User;

import java.util.Map;
import java.util.stream.Collectors;

public class DtoConverter {
    public static DtoUser toDto(User user) {
        DtoUser dtoUser = new DtoUser();
        dtoUser.setId(user.getId());
        dtoUser.setFirstName(user.getFirstName());
        dtoUser.setLastName(user.getLastName());
        dtoUser.setAddress(user.getAddress());
        dtoUser.setEmail(user.getEmail());
        dtoUser.setPassword(user.getPassword());
        dtoUser.setVersion(user.getVersion());
        dtoUser.setCreated(user.getCreated());
        dtoUser.setUpdated(user.getUpdated());
        return dtoUser;
    }

    public static User fromDto(DtoUser dtoUser) {
        User user = new User();
        user.setId(dtoUser.getId());
        user.setFirstName(dtoUser.getFirstName());
        user.setLastName(dtoUser.getLastName());
        user.setAddress(dtoUser.getAddress());
        user.setEmail(dtoUser.getEmail());
        user.setPassword(dtoUser.getPassword());
        user.setVersion(dtoUser.getVersion());
        user.setCreated(dtoUser.getCreated());
        user.setUpdated(dtoUser.getUpdated());
        return user;
    }

    public static DtoOrder toDto(Order order) {
        DtoOrder dtoOrder = new DtoOrder();
        dtoOrder.setId(order.getId());
        dtoOrder.setName(order.getName());
        dtoOrder.setUser(toDto(order.getUser()));
        dtoOrder.setProductListWithAmount(order.getProductListWithAmount().entrySet().stream()
                .collect(Collectors.toMap(
                        (entry) -> DtoConverter.toDto(entry.getKey()),
                        Map.Entry::getValue
                )));
        dtoOrder.setVersion(order.getVersion());
        dtoOrder.setCreated(order.getCreated());
        dtoOrder.setUpdated(order.getUpdated());
        return dtoOrder;
    }

    public static Order fromDto(DtoOrder dtoOrder) {
        Order order = new Order();
        order.setId(dtoOrder.getId());
        order.setName(dtoOrder.getName());
        order.setUser(fromDto(dtoOrder.getUser()));
        order.setProductListWithAmount(dtoOrder.getProductListWithAmount().entrySet().stream()
                .collect(Collectors.toMap(
                        (entry) -> DtoConverter.fromDto(entry.getKey()),
                        Map.Entry::getValue
                )));
        order.setVersion(dtoOrder.getVersion());
        order.setCreated(dtoOrder.getCreated());
        order.setUpdated(dtoOrder.getUpdated());
        return order;
    }

    public static DtoProduct toDto(Product product) {
        DtoProduct dtoProduct = new DtoProduct();
        dtoProduct.setId(product.getId());
        dtoProduct.setCategory(product.getCategory());
        dtoProduct.setName(product.getName());
        dtoProduct.setPrice(product.getPrice());
        dtoProduct.setDiscount(product.getDiscount());
        dtoProduct.setVersion(product.getVersion());
        dtoProduct.setCreated(product.getCreated());
        dtoProduct.setUpdated(product.getUpdated());
        return dtoProduct;
    }

    public static Product fromDto(DtoProduct dtoProduct) {
        Product product = new Product();
        product.setId(dtoProduct.getId());
        product.setCategory(dtoProduct.getCategory());
        product.setName(dtoProduct.getName());
        product.setPrice(dtoProduct.getPrice());
        product.setDiscount(dtoProduct.getDiscount());
        product.setVersion(dtoProduct.getVersion());
        product.setCreated(dtoProduct.getCreated());
        product.setUpdated(dtoProduct.getUpdated());
        return product;
    }
}
