package com.example.Te.hno_store.Cart;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.Te.hno_store.Product.Product;
import com.example.Te.hno_store.Product.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public Cart createCart(){
        log.info("Creating new cart");
        var cart = new Cart();
        return cartRepository.save(cart);
        
    }

    public Cart getCart(Long id){
        log.info("Getting cart by ID: {}", id);
        return cartRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Cart with ID {} not found", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found");
                });
    }
    public Cart addProduct(Long cartId, Long productId, int quantity) {
        log.info("Adding product to cart: {}", productId);
        Cart cart = getCart(cartId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.error("Product with ID {} not found when adding to cart {}", productId, cartId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
                });
        for (CartItem item : cart.getItems()) {
            if (item.getProduct().getId().equals(productId)) {
                item.setQuantity(item.getQuantity() + quantity);
                return cartRepository.save(cart);
            }
        }
        CartItem newItem = new CartItem();
        newItem.setProduct(product);
        newItem.setQuantity(quantity);
        newItem.setProductName(product.getName());
        newItem.setCart(cart);
        cart.getItems().add(newItem);
        return cartRepository.save(cart);
    }
    public Cart removeProduct(Long cartId, Long productId){
        log.info("Removing product from cart: {}", productId);
        Cart cart = getCart(cartId);
        cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        return cartRepository.save(cart);
    }

    public Cart clearCart(Long cartId){
        log.info("Clearing cart: {}", cartId);
        Cart cart = getCart(cartId);
        cart.getItems().clear();
        return cartRepository.save(cart);
    }
    public Cart getCartWithItems(Long id) {
        log.info("Getting cart with items by ID: {}", id);
        return cartRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Cart with ID {} not found when getting with items", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found");
                });
    }
}