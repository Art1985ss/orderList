package com.art1985.orderList.service.order.exception;

import java.util.NoSuchElementException;

public class OrderNotFoundException extends NoSuchElementException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
