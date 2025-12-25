package com.example.Te.hno_store.Warehouse;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Warehouse>> getAll() {
        log.info("Getting all warehouses");
        List<Warehouse> warehouses = warehouseService.getAll();
        return ResponseEntity.ok(warehouses);
    }

    @PostMapping
    public ResponseEntity<Warehouse> add(@RequestBody Warehouse warehouse) {
        log.info("Adding warehouse: {}", warehouse);
        Warehouse savedWarehouse = warehouseService.add(warehouse);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedWarehouse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        log.info("Removing warehouse by id: {}", id);
        warehouseService.removeById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{productId}/decrease")
    public ResponseEntity<Void> decreaseQuantity(@PathVariable Long productId, @RequestParam int amount) {
        log.info("Decreasing quantity of product: {}", productId);
        warehouseService.decreaseQuantity(productId, amount);
        return ResponseEntity.ok().build();
    }
}
