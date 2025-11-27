package com.example.Te.hno_store.Warehouse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/warehouse")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @GetMapping
    public List<Warehouse> getAll() {
        return warehouseService.getAll();
    }

    @PostMapping
    public Warehouse add(@RequestBody Warehouse warehouse) {
        return warehouseService.add(warehouse);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        warehouseService.removeById(id);
    }
    @PutMapping("/{productId}/decrease")
    public void decreaseQuantity(@PathVariable Long productId, @RequestParam int amount) {
        warehouseService.decreaseQuantity(productId, amount);
    }

}
