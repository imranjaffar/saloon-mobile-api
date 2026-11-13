package com.saloon.saloon_mobile_api.controller;

import com.saloon.saloon_mobile_api.dto.InventoryTransactionResponse;
import com.saloon.saloon_mobile_api.dto.StockAdjustmentRequest;
import com.saloon.saloon_mobile_api.dto.StockDashboardResponse;
import com.saloon.saloon_mobile_api.dto.StockDeductionRequest;
import com.saloon.saloon_mobile_api.dto.StockInRequest;
import com.saloon.saloon_mobile_api.dto.StockOutRequest;
import com.saloon.saloon_mobile_api.dto.StockResponse;
import com.saloon.saloon_mobile_api.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import com.saloon.saloon_mobile_api.utility.PaginationSupport;

import java.util.List;

@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    /**
     * Purchase / Stock In
     */
    @PostMapping("/in")
    public ResponseEntity<StockResponse> stockIn(
            @Valid @RequestBody StockInRequest request) {

        return ResponseEntity.ok(
                stockService.stockIn(request)
        );
    }

    /**
     * Manual Stock Out
     */
    @PostMapping("/out")
    public ResponseEntity<StockResponse> stockOut(
            @Valid @RequestBody StockOutRequest request) {

        return ResponseEntity.ok(
                stockService.stockOut(request)
        );
    }

    /**
     * Manual Stock Adjustment
     */
    @PostMapping("/adjust")
    public ResponseEntity<StockResponse> adjustStock(
            @Valid @RequestBody StockAdjustmentRequest request) {

        return ResponseEntity.ok(
                stockService.adjustStock(request)
        );
    }

    /**
     * Appointment / Sale Deduction
     */
    @PostMapping("/deduct")
    public ResponseEntity<StockResponse> deductStock(
            @Valid @RequestBody StockDeductionRequest request) {

        return ResponseEntity.ok(
                stockService.deductStock(request)
        );
    }

    /**
     * Stock Transaction History
     */
    @GetMapping("/{inventoryId}/history")
    public ResponseEntity<List<InventoryTransactionResponse>> history(
            @PathVariable Long inventoryId) {

        return ResponseEntity.ok(
                stockService.history(inventoryId)
        );
    }

    @GetMapping("/{inventoryId}/history/page")
    public ResponseEntity<List<InventoryTransactionResponse>> historyPage(
            @PathVariable Long inventoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PaginationSupport.page(page, size);
        return ResponseEntity.ok(
                stockService.history(inventoryId, pageable)
        );
    }

    /**
     * Inventory Dashboard
     */
    @GetMapping("/dashboard")
    public ResponseEntity<StockDashboardResponse> dashboard() {

        return ResponseEntity.ok(
                stockService.dashboard()
        );
    }
}