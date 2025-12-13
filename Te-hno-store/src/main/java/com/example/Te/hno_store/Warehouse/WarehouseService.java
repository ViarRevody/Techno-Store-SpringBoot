package com.example.Te.hno_store.Warehouse;

import java.util.List;

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
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final ProductRepository productRepository;

    public List<Warehouse> getAll(){
        return warehouseRepository.findAll();
    }
    public void removeById(Long id) {
        log.info("Removing warehouse by id: {}", id);
        warehouseRepository.deleteById(id);
    }
    public Warehouse add(Warehouse warehouse) {
        log.info("Adding warehouse: {}", warehouse);
        Product product = productRepository.findById(warehouse.getProduct().getId())
                .orElseThrow(() -> {
                    log.error("Product with ID {} not found when adding warehouse", warehouse.getProduct().getId());
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
                });
        warehouse.setProductName(product.getName());
        return warehouseRepository.save(warehouse);
    }

    public List<Warehouse> getByProductId(Long productId) {
        log.info("Getting warehouse by product ID: {}", productId);
        return warehouseRepository.findByProduct_Id(productId);
    }
    public void decreaseQuantity(Long productId, int amount) {
        log.info("Decreasing quantity of product: {}", productId);
        List<Warehouse> warehouses = warehouseRepository.findByProduct_Id(productId);
        if (warehouses.isEmpty()) {
            throw new IllegalStateException("Товар не найден на складе!");
        }
        Warehouse w = warehouses.get(0); 
        if (w.getQuantity() >= amount) {
            w.setQuantity(w.getQuantity() - amount);
            warehouseRepository.save(w);
        } else {
            throw new IllegalStateException("Недостаточно товара на складе!");
        }
    }
}
