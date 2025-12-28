package com.example.techno_store.Product;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAll() {
        log.info("Getting all products");
        return productRepository.findAll();
    }

    public Product getOne(Long id) {
        log.info("Getting product by id: {}", id);
        return productRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Product with id {} not found", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
                });
    }

    public Product create(Product product) {
        log.info("Creating product: {}", product);
        return productRepository.save(product);
    }

    public void delete(Long id) {
        log.info("Deleting product by id: {}", id);
        Product product =getOne(id);
        productRepository.delete(product);
    }

    public Product update(Long id, Product updatedProduct) {
        log.info("Updating product by id: {}", id);
        Product product = getOne(id);

        product.setName(updatedProduct.getName());
        product.setPrice(updatedProduct.getPrice());
        product.setCategory(updatedProduct.getCategory());

        return productRepository.save(product);
    }
}
