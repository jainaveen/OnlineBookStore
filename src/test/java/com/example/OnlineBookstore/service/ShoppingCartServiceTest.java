package com.example.OnlineBookstore.service;

import com.example.OnlineBookstore.model.ShoppingCart;
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

class ShoppingCartServiceTest {

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @InjectMocks
    private ShoppingCartService shoppingCartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void testGetCartItems() {
        Long userId = 1L;
        List<ShoppingCart> cartItems = Arrays.asList(
                new ShoppingCart(userId, 101L, 2),
                new ShoppingCart(userId, 102L, 1)
        );

        when(shoppingCartRepository.findByUserId(userId)).thenReturn(cartItems);

        List<ShoppingCart> result = shoppingCartService.getCartItems(userId);

        assertEquals(2, result.size());
        verify(shoppingCartRepository, times(1)).findByUserId(userId);
    }

    @Test
    void testRemoveCartItem() {
        Long cartId = 1001L;

        doNothing().when(shoppingCartRepository).deleteById(cartId);

        shoppingCartService.removeCartItem(cartId);

        verify(shoppingCartRepository, times(1)).deleteById(cartId);
    }

    @Test
    void testClearCart() {
        Long userId = 1L;
        List<ShoppingCart> cartItems = Arrays.asList(
                new ShoppingCart(userId, 101L, 2),
                new ShoppingCart(userId, 102L, 1)
        );

        when(shoppingCartRepository.findByUserId(userId)).thenReturn(cartItems);
        doNothing().when(shoppingCartRepository).deleteAll(cartItems);

        shoppingCartService.clearCart(userId);

        verify(shoppingCartRepository, times(1)).findByUserId(userId);
        verify(shoppingCartRepository, times(1)).deleteAll(cartItems);
    }
}