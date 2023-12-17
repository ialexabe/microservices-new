package com.leons.inventoryservice.repository;

import com.leons.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface InventoryRepository extends JpaRepository<Inventory,Long> {

    public List<Inventory> findBySkuCodeIn(List<String> skuCode);
}
