package com.art1985.orderList.web.controller;

import com.art1985.orderList.entities.Order;
import com.art1985.orderList.entities.Product;
import com.art1985.orderList.entities.User;
import com.art1985.orderList.service.order.OrderService;
import com.art1985.orderList.service.product.ProductService;
import com.art1985.orderList.service.user.UserService;
import com.art1985.orderList.web.dto.DtoConverter;
import com.art1985.orderList.web.dto.DtoOrder;
import com.art1985.orderList.web.dto.DtoProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class ActionsController {
    private UserService userService;
    private OrderService orderService;
    private ProductService productService;

    @Autowired
    public ActionsController(UserService userService, OrderService orderService, ProductService productService) {
        this.userService = userService;
        this.orderService = orderService;
        this.productService = productService;
    }

    @GetMapping("/user/orders")
    public ResponseEntity<List<DtoOrder>> findUserOrders(Principal principal) {
        User user = userService.findByEmail(principal.getName());
        List<DtoOrder> orders = orderService.findByUserId(user.getId()).stream().map(DtoConverter::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/user/orders")
    public ResponseEntity<DtoOrder> createUserOrder(Principal principal, @RequestBody DtoOrder dtoOrder) {
        User user = userService.findByEmail(principal.getName());
        Order order = DtoConverter.fromDto(dtoOrder);
        order.setUser(user);
        return ResponseEntity.ok(DtoConverter.toDto(orderService.create(order)));
    }

    @GetMapping("/order/{order_id}/products")
    public ResponseEntity<Map<DtoProduct, Integer>> findOrderProducts(@PathVariable(name = "order_id") Long orderId) {
        Map<Product, Integer> products;
        Order order = orderService.findById(orderId);
        products = order.getProductListWithAmount();
        Map<DtoProduct, Integer> productIntegerMap = products.entrySet().stream().collect(Collectors.toMap(
                (entry)-> DtoConverter.toDto(entry.getKey()),
                Map.Entry::getValue
        ));
        return ResponseEntity.ok(productIntegerMap);
    }

    @GetMapping("/order/{order_id}/product/{product_id}/{count}")
    public ResponseEntity<DtoOrder> addToOrder(@PathVariable(name = "order_id") Long orderId,
                                            @PathVariable(name = "product_id") Long productId,
                                            @PathVariable(name = "count") int count) {
        Order order = orderService.findById(orderId);
        Product product = productService.findById(productId);
        order.addProduct(product, count);
        DtoOrder dtoOrder = DtoConverter.toDto(order);
        return ResponseEntity.ok(dtoOrder);
    }

    @GetMapping("/order/{order_id}/product/{product_id}")
    public ResponseEntity<DtoOrder> removeFromOrder(@PathVariable(name = "order_id") Long orderId, @PathVariable(name = "product_id") Long productId) {
        Order order = orderService.findById(orderId);
        Product product = productService.findById(productId);
        order.removeProduct(product);
        return ResponseEntity.ok(DtoConverter.toDto(order));
    }

    @GetMapping("/order/{order_id}/price")
    public ResponseEntity<BigDecimal> getOrderPrice(@PathVariable(name = "order_id") Long orderId) {
        BigDecimal price = orderService.findById(orderId).getTotalPrice();
        return ResponseEntity.ok(price);
    }
}
