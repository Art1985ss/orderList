package com.art1985.orderList.web.controller;

import com.art1985.orderList.entities.Order;
import com.art1985.orderList.entities.Product;
import com.art1985.orderList.entities.User;
import com.art1985.orderList.service.order.OrderService;
import com.art1985.orderList.service.product.ProductService;
import com.art1985.orderList.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
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
    public ResponseEntity<List<Order>> findUserOrders(Principal principal) {
        User user = userService.findByName(principal.getName());
        List<Order> orders = orderService.findByUserId(user.getId());
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/user/orders")
    public ResponseEntity<Order> createUserOrder(Principal principal, @RequestBody Order order) {
        User user = userService.findByName(principal.getName());
        order.setUser(user);
        orderService.create(order);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/order/{order_id}/products")
    public ResponseEntity<Map<Product, Integer>> findOrderProducts(@PathVariable Long orderId) {
        Map<Product, Integer> products;
        Order order = orderService.findById(orderId);
        products = order.getProductListWithAmount();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/order/{order_id}/product/{product_id}/{count}")
    public ResponseEntity<Order> addToOrder(@PathVariable(name = "order_id") Long orderId,
                                            @PathVariable(name = "product_id") Long productId,
                                            @PathVariable(name = "count") int count) {
        Order order = orderService.findById(orderId);
        Product product = productService.findById(productId);
        order.addProduct(product, count);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/order/{order_id}/product/{product_id}")
    public ResponseEntity<Order> removeFromOrder(@PathVariable(name = "order_id") Long orderId, @PathVariable(name = "product_id") Long productId) {
        Order order = orderService.findById(orderId);
        Product product = productService.findById(productId);
        order.removeProduct(product);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/order/{order_id}/price")
    public ResponseEntity<BigDecimal> getOrderPrice(@PathVariable Long orderId) {
        BigDecimal price = orderService.findById(orderId).getTotalPrice();
        return ResponseEntity.ok(price);
    }
}
