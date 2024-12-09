package com.example.OnlineBookstore.controller;
import com.example.OnlineBookstore.model.ShoppingCart;
import com.example.OnlineBookstore.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public ResponseEntity<ShoppingCart> addToCart(@RequestParam Long userId,
                                                  @RequestParam Long bookId,
                                                  @RequestParam int quantity) {
        ShoppingCart cartItem = shoppingCartService.addToCart(userId, bookId, quantity);
        return ResponseEntity.ok(cartItem);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<ShoppingCart>> getCartItems(@PathVariable Long userId) {
        return ResponseEntity.ok(shoppingCartService.getCartItems(userId));
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable Long cartId) {
        shoppingCartService.removeCartItem(cartId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        shoppingCartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
