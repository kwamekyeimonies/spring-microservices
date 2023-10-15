package com.inventoryservice.inventoryservice.controller;


import com.inventoryservice.inventoryservice.dto.InventoryResponse;
import com.inventoryservice.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse>  isInStock(@RequestParam List<String> skuCode){
        System.out.println("Received inventory check the request "+skuCode);
        return inventoryService.isInStock(skuCode);
    }
}
