package com.example.Te.hno_store.Warehouse;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping("/api/warehouse")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @GetMapping
    public List<Warehouse> getAll() {
        log.info("Getting all warehouses");
        return warehouseService.getAll();
    }

    @PostMapping
    public Warehouse add(@RequestBody Warehouse warehouse) {
        log.info("Adding warehouse: {}", warehouse);
        return warehouseService.add(warehouse);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        log.info("Removing warehouse by id: {}", id);
        warehouseService.removeById(id);
    }
    @PutMapping("/{productId}/decrease")
    public void decreaseQuantity(@PathVariable Long productId, @RequestParam int amount) {
        log.info("Decreasing quantity of product: {}", productId);
        warehouseService.decreaseQuantity(productId, amount);
    }

}
