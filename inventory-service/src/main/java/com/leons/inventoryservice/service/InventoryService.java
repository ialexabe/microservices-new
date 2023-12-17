package com.leons.inventoryservice.service;

import com.leons.inventoryservice.dto.InventoryResponse;
import com.leons.inventoryservice.model.Inventory;
import com.leons.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public List<InventoryResponse> isInStock(List<String> skuCode){
         return inventoryRepository.findBySkuCodeIn(skuCode).stream().map(inventory ->
             InventoryResponse.builder()
                     .skuCode(inventory.getSkuCode())
                     .isInStock(inventory.getQuantity().intValue() > 0)
                     .build()
         ).toList();
    }

}
