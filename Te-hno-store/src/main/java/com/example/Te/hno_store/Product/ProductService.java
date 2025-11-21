package com.example.Te.hno_store.Product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getOne(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Product with id " + id + " not found"));
    }

    public Product create(Product product) {
        return productRepository.save(product);
    }

    public void delete(Long id) {
        Product product =getOne(id);
        productRepository.delete(product);
    }

    public Product update(Long id, Product updatedProduct) {
        Product product = getOne(id);

        product.setName(updatedProduct.getName());
        product.setPrice(updatedProduct.getPrice());
        product.setCategory(updatedProduct.getCategory());

        return productRepository.save(product);
    }
}
