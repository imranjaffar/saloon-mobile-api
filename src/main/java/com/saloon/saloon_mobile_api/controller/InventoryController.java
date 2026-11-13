package com.saloon.saloon_mobile_api.controller;

import com.saloon.saloon_mobile_api.dto.CreateInventoryRequest;
import com.saloon.saloon_mobile_api.dto.InventoryResponse;
import com.saloon.saloon_mobile_api.dto.UpdateInventoryRequest;
import com.saloon.saloon_mobile_api.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import com.saloon.saloon_mobile_api.utility.PaginationSupport;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;


    @PostMapping
    public ResponseEntity<InventoryResponse> create(
            @Valid @RequestBody CreateInventoryRequest request) {

        InventoryResponse response =
                inventoryService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    @GetMapping
    public ResponseEntity<List<InventoryResponse>> getAll() {

        return ResponseEntity.ok(
                inventoryService.getAll()
        );
    }

    @GetMapping("/page")
    public ResponseEntity<List<InventoryResponse>> getPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "false") boolean lowStockOnly) {
        Pageable pageable = PaginationSupport.page(page, size);
        return ResponseEntity.ok(
                inventoryService.getPage(pageable, search, lowStockOnly)
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<InventoryResponse> getById(
            @PathVariable Long id) throws Exception {

        return ResponseEntity.ok(
                inventoryService.getById(id)
        );
    }


    @GetMapping("/low-stock")
    public ResponseEntity<List<InventoryResponse>> lowStock() {

        return ResponseEntity.ok(
                inventoryService.getLowStock()
        );
    }

    @GetMapping("/low-stock/page")
    public ResponseEntity<List<InventoryResponse>> lowStockPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(
                inventoryService.getLowStock(
                        PaginationSupport.page(page, size)
                )
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<InventoryResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateInventoryRequest request) throws Exception {


        return ResponseEntity.ok(
                inventoryService.update(id, request)
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) throws Exception {


        inventoryService.delete(id);

        return ResponseEntity.noContent().build();
    }
}