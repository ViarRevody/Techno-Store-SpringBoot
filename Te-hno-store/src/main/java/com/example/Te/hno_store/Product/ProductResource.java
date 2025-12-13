package com.example.Te.hno_store.Product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductResource {

    private final ProductRepository productRepository;

    @GetMapping
    public List<Product> getAll(Pageable pageable) {
        log.info("Getting all products");
        return productRepository.findAll(pageable.getSort());
    }

    @GetMapping("/{id}")
    public Product getOne(@PathVariable Long id) {
        log.info("Getting product by id: {}", id);
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.orElseThrow(() -> {
            log.error("Product with id {} not found", id);
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        });
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        log.info("Creating product: {}", product);
        return productRepository.save(product);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product updatedProduct) {
        log.info("Updating product by id: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Product with id {} not found", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
                });

        product.setName(updatedProduct.getName());
        product.setPrice(updatedProduct.getPrice());
        product.setCategory(updatedProduct.getCategory());

        return productRepository.save(product);
    }

    @DeleteMapping("/{id}")
    public void  delete(@PathVariable Long id) {
        log.info("Deleting product by id: {}", id);
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            productRepository.delete(product);
        }
    }
}
