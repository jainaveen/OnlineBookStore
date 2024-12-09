package com.example.OnlineBookstore.controller;

import com.example.OnlineBookstore.model.Order;
import com.example.OnlineBookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
    }

    @PostMapping("/checkout/{userId}")
    public ResponseEntity<Void> checkout(@PathVariable Long userId) {
        orderService.checkout(userId);
        return ResponseEntity.ok().build();
    }
}
