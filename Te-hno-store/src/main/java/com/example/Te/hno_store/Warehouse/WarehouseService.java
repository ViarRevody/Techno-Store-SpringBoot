package com.example.Te.hno_store.Warehouse;

import com.example.Te.hno_store.Product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final ProductRepository productRepository;

    public List<Warehouse> getAll(){
        return warehouseRepository.findAll();
    }

    public Warehouse add(Warehouse warehouse){
        return warehouseRepository.save(warehouse);
    }

    public void removeById(Long id) {
        warehouseRepository.deleteById(id); // встроенный метод JpaRepository
    }


    public List<Warehouse> getByProductId(Long productId) {
        return warehouseRepository.findByProduct_Id(productId);
    }
    public void decreaseQuantity(Long productId, int amount) {
        List<Warehouse> warehouses = warehouseRepository.findByProduct_Id(productId);
        if (warehouses.isEmpty()) {
            throw new IllegalStateException("Товар не найден на складе!");
        }
        Warehouse w = warehouses.get(0); // берём первую запись
        if (w.getQuantity() >= amount) {
            w.setQuantity(w.getQuantity() - amount);
            warehouseRepository.save(w);
        } else {
            throw new IllegalStateException("Недостаточно товара на складе!");
        }
    }
}
