package com.example.OnlineBookstore.service;
import com.example.OnlineBookstore.model.ShoppingCart;
import com.example.OnlineBookstore.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    public ShoppingCart addToCart(Long userId, Long bookId, int quantity) {
        ShoppingCart item = new ShoppingCart(userId, bookId, quantity);
        return shoppingCartRepository.save(item);
    }

    public List<ShoppingCart> getCartItems(Long userId) {
        return shoppingCartRepository.findByUserId(userId);
    }

    public void removeCartItem(Long cartId) {
        shoppingCartRepository.deleteById(cartId);
    }

    public void clearCart(Long userId) {
        List<ShoppingCart> cartItems = shoppingCartRepository.findByUserId(userId);
        shoppingCartRepository.deleteAll(cartItems);
    }
}
