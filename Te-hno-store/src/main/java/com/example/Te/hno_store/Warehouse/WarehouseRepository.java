package com.example.Te.hno_store.Warehouse;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WarehouseRepository extends JpaRepository<Warehouse,Long> {
    List <Warehouse> findByProduct_Id(Long productId);
}
