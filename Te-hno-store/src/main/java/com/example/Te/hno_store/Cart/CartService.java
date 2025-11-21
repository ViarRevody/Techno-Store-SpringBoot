package com.example.Te.hno_store.Cart;

import com.example.Te.hno_store.Product.Product;
import com.example.Te.hno_store.Product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.plaf.PanelUI;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public Cart createCart(){
        var cart = new Cart();
        return cartRepository.save(cart);
    }

    public Cart getCart(Long id){
        return cartRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Cart not found"));
    }
    public Cart addProduct(Long cartId, Long productId, int quantity) {
        Cart cart = getCart(cartId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        for (CartItem item : cart.getItems()) {
            if (item.getProduct().getId().equals(productId)) {
                item.setQuantity(item.getQuantity() + quantity);
                return cartRepository.save(cart);
            }
        }
        CartItem newItem = new CartItem();
        newItem.setProduct(product);
        newItem.setQuantity(quantity);
        cart.getItems().add(newItem);
        return cartRepository.save(cart);
    }
    public Cart removeProduct(Long cartId, Long productId){
        Cart cart = getCart(cartId);
        cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        return cartRepository.save(cart);
    }

    public Cart clearCart(Long cartId){
        Cart cart = getCart(cartId);
        cart.getItems().clear();
        return cartRepository.save(cart);
    }
}