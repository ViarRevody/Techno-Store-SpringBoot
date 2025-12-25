package com.example.Te.hno_store.Cart;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    private final CartRepository cartRepository;

    @GetMapping("/{id}")
        public ResponseEntity<Cart> getCart(@PathVariable Long id){
            log.info("GET cart request for cart ID: {}", id);
        Cart cart = cartService.getCart(id);
        return ResponseEntity.ok(cart);
        }
        @PostMapping
        public ResponseEntity<Cart> createCart(){
            log.info("New cart created with createCart");
            Cart cart = cartService.createCart();
            return ResponseEntity.status(HttpStatus.CREATED).body(cart);
        }
    
        @PostMapping("/{id}/add")
        public ResponseEntity<Cart> addProduct(@PathVariable Long id,
                @RequestParam Long productId,
                @RequestParam int quantity){
            log.info("The method has worked addProduct - Cart ID: {}, Product ID: {}, Quantity: {}", id, productId, quantity);
            Cart cart =cartService.addProduct(id,productId,quantity);
            return ResponseEntity.ok(cart);
        }
    
        @DeleteMapping("/{id}/remove")
        public ResponseEntity< Cart> removeProduct(
            @PathVariable Long id,
            @RequestParam Long productId){
            log.info("The method has worked removeProduct - Cart ID: {}, Product ID: {}", id, productId);
            Cart cart = cartService.removeProduct(id,productId);
            return ResponseEntity.ok(cart);
        }
    
        @DeleteMapping("/{id}/clear")
        public ResponseEntity<Cart> clearCart(@PathVariable Long id) {
            log.info("The method has worked clearCart - Cart ID: {}", id);
            Cart cart=cartService.clearCart(id);
            return ResponseEntity.ok(cart);
        }
}

