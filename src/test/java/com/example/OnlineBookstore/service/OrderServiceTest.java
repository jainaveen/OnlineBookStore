package com.example.OnlineBookstore.service;

import com.example.OnlineBookstore.model.Order;
import com.example.OnlineBookstore.model.ShoppingCart;
import com.example.OnlineBookstore.repository.OrderRepository;
import com.example.OnlineBookstore.repository.ShoppingCartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPlaceOrder() {
        Long userId = 1L;
        Long bookId = 101L;
        int quantity = 2;
        double totalPrice = 59.98;

        Order order = new Order(userId, bookId, quantity, totalPrice);
        when(orderRepository.save(order)).thenReturn(order);

        Order placedOrder = orderService.placeOrder(userId, bookId, quantity, totalPrice);

        assertNotNull(placedOrder);
        assertEquals(userId, placedOrder.getUserId());
        assertEquals(bookId, placedOrder.getBookId());
        assertEquals(quantity, placedOrder.getQuantity());
        assertEquals(totalPrice, placedOrder.getTotalPrice());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testGetOrdersByUserId() {
        Long userId = 1L;
        List<Order> orders = Arrays.asList(
                new Order(userId, 101L, 1, 29.99),
                new Order(userId, 102L, 2, 59.98)
        );

        when(orderRepository.findByUserId(userId)).thenReturn(orders);

        List<Order> userOrders = orderService.getOrdersByUserId(userId);

        assertEquals(2, userOrders.size());
        verify(orderRepository, times(1)).findByUserId(userId);
    }

    @Test
    void testCheckout() {
        Long userId = 1L;
        List<ShoppingCart> cartItems = Arrays.asList(
                new ShoppingCart(userId, 101L, 2),
                new ShoppingCart(userId, 102L, 1)
        );

        when(shoppingCartRepository.findByUserId(userId)).thenReturn(cartItems);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        orderService.checkout(userId);

        // Verify that orders were placed
        verify(orderRepository, times(2)).save(any(Order.class));

        // Verify that the cart was cleared
        verify(shoppingCartRepository, times(1)).deleteAll(cartItems);
    }

    @Test
    void testGetBookPrice() {
        Long bookId = 101L;

        // Since getBookPrice is private, you can test it indirectly through checkout
        // Here, assume it's always 29.99 as per placeholder logic
        double expectedPrice = 29.99;

        assertEquals(expectedPrice, orderService.getBookPrice(bookId));
    }
}
