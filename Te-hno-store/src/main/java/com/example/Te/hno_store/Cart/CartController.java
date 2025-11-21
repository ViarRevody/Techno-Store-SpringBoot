package com.example.Te.hno_store.Cart;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    @GetMapping("/{id}")
    public Cart getCart(@PathVariable Long id){
        return cartService.getCart(id);
    }
    @PostMapping
    public Cart createCart(){
        return cartService.createCart();
    }

    @PostMapping("/{id}/add")
    public Cart addProduct(@PathVariable Long id,
            @RequestParam Long productId,
            @RequestParam int quantity){
        return cartService.addProduct(id,productId,quantity);
    }

    @DeleteMapping("/{id}/remove")
    public Cart removeProduct(
        @PathVariable Long id,
        @RequestParam Long productId){
        return cartService.removeProduct(id, productId);
    }

    @DeleteMapping("/{id}/clear")
    public Cart clearCart(@PathVariable Long id) {
        return cartService.clearCart(id);
    }
}

