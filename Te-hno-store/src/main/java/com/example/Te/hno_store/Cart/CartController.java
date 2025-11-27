package com.example.Te.hno_store.Cart;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    @GetMapping("/{id}")
    public Cart getCart(@PathVariable Long id){
        log.info("GET cart request for cart ID: {}", id);
        return cartService.getCart(id);
    }
    @PostMapping
    public Cart createCart(){
        log.info("New cart created with createCart");
        return cartService.createCart();
    }

    @PostMapping("/{id}/add")
    public Cart addProduct(@PathVariable Long id,
            @RequestParam Long productId,
            @RequestParam int quantity){
        log.info("The method has worked addProduct - Cart ID: {}, Product ID: {}, Quantity: {}", id, productId, quantity);
        return cartService.addProduct(id,productId,quantity);
    }

    @DeleteMapping("/{id}/remove")
    public Cart removeProduct(
        @PathVariable Long id,
        @RequestParam Long productId){
        log.info("The method has worked removeProduct - Cart ID: {}, Product ID: {}", id, productId);
        return cartService.removeProduct(id, productId);
    }

    @DeleteMapping("/{id}/clear")
    public Cart clearCart(@PathVariable Long id) {
        log.info("The method has worked clearCart - Cart ID: {}", id);
        return cartService.clearCart(id);
    }
}

