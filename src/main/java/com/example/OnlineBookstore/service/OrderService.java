package com.example.OnlineBookstore.service;
import com.example.OnlineBookstore.model.Order;
import com.example.OnlineBookstore.model.ShoppingCart;
import com.example.OnlineBookstore.repository.OrderRepository;
import com.example.OnlineBookstore.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    public Order placeOrder(Long userId, Long bookId, int quantity, double totalPrice) {
        Order order = new Order(userId, bookId, quantity, totalPrice);
        return orderRepository.save(order);
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public void checkout(Long userId) {
        List<ShoppingCart> cartItems = shoppingCartRepository.findByUserId(userId);

        for (ShoppingCart item : cartItems) {
            double totalPrice = item.getQuantity() * getBookPrice(item.getBookId());
            placeOrder(userId, item.getBookId(), item.getQuantity(), totalPrice);
        }

        // Clear cart after checkout
        shoppingCartRepository.deleteAll(cartItems);
    }

    public double getBookPrice(Long bookId) {
        // Assume a method to fetch book price from database
        // Replace this with an actual repository call
        return 29.99; // Placeholder
    }
}
