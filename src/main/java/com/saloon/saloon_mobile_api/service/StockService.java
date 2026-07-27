package com.saloon.saloon_mobile_api.service;

import com.saloon.saloon_mobile_api.dto.*;
import com.saloon.saloon_mobile_api.entity.*;
import com.saloon.saloon_mobile_api.mapper.InventoryTransactionMapper;
import com.saloon.saloon_mobile_api.repository.InventoryRepository;
import com.saloon.saloon_mobile_api.repository.InventoryTransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
public class StockService {

    private final InventoryRepository inventoryRepository;
    private final InventoryTransactionRepository transactionRepository;

    private final InventoryTransactionMapper transactionMapper;

    /**
     * Purchase / Stock In
     */
    public StockResponse stockIn(StockInRequest request) {

        InventoryItem item = getItem(request.getInventoryId());

        BigDecimal stockBefore = item.getAvailableQuantity();

        BigDecimal quantity = convertQuantity(
                item,
                request.getQuantity(),
                request.getQuantityMode()
        );

        BigDecimal stockAfter = stockBefore.add(quantity);

        BigDecimal totalAmount =
                request.getUnitPrice()
                        .multiply(request.getQuantity());

        saveTransaction(
                item,
                stockBefore,
                stockAfter,
                quantity,
                TransactionType.STOCK_IN,
                request.getReferenceType(),
                request.getReferenceId(),
                request.getSupplierName(),
                request.getUnitPrice(),
                totalAmount,
                request.getRemarks()
        );

        return toResponse(item);
    }

    /**
     * Manual Stock Out
     */
    public StockResponse stockOut(StockOutRequest request) {

        InventoryItem item = getItem(request.getInventoryId());

        BigDecimal stockBefore = item.getAvailableQuantity();

        BigDecimal quantity = convertQuantity(
                item,
                request.getQuantity(),
                request.getQuantityMode()
        );

        if (stockBefore.compareTo(quantity) < 0) {
            throw new IllegalArgumentException("Insufficient stock.");
        }

        BigDecimal stockAfter = stockBefore.subtract(quantity);

        saveTransaction(
                item,
                stockBefore,
                stockAfter,
                quantity.negate(),
                TransactionType.RETURN,
                request.getReferenceType(),
                request.getReferenceId(),
                null,
                null,
                null,
                request.getRemarks()
        );

        return toResponse(item);
    }

    /**
     * Manual Adjustment
     */
    public StockResponse adjustStock(StockAdjustmentRequest request) {

        InventoryItem item = getItem(request.getInventoryId());

        BigDecimal stockBefore = item.getAvailableQuantity();

        BigDecimal newStock = convertQuantity(
                item,
                request.getNewQuantity(),
                request.getQuantityMode()
        );

        BigDecimal difference = newStock.subtract(stockBefore);

        saveTransaction(
                item,
                stockBefore,
                newStock,
                difference,
                TransactionType.ADJUSTMENT,
                ReferenceType.MANUAL,
                null,
                null,
                null,
                null,
                request.getReason()
        );

        return toResponse(item);
    }

    /**
     * Future Appointment deduction
     */
    public StockResponse deductStock(StockDeductionRequest request) {

        InventoryItem item = getItem(request.getInventoryId());

        BigDecimal stockBefore = item.getAvailableQuantity();

        BigDecimal deduction = convertQuantity(
                item,
                request.getQuantity(),
                request.getQuantityMode()
        );

        if (stockBefore.compareTo(deduction) < 0) {
            throw new IllegalArgumentException("Insufficient stock.");
        }

        BigDecimal stockAfter = stockBefore.subtract(deduction);

        saveTransaction(
                item,
                stockBefore,
                stockAfter,
                deduction.negate(),
                TransactionType.ORDER_DEDUCTION,
                ReferenceType.MANUAL,
                request.getReferenceId(),
                null,
                null,
                null,
                request.getRemarks()
        );

        return toResponse(item);
    }

    @Transactional(readOnly = true)
    public List<InventoryTransactionResponse> history(Long inventoryId) {

        return transactionRepository
                .findByInventoryItemIdOrderByCreatedAtDesc(inventoryId)
                .stream()
                .map(transactionMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public StockDashboardResponse dashboard() {

        return StockDashboardResponse.builder()
                .totalItems(inventoryRepository.count())
                .activeItems(inventoryRepository.countByActiveTrue())
                .inactiveItems(inventoryRepository.countByActiveFalse())
                .lowStockItems(inventoryRepository.countLowStockItems())
                .totalTransactions(transactionRepository.count())
                .build();
    }

    //-----------------------------------------------------
    // Helpers
    //-----------------------------------------------------

    private InventoryItem getItem(Long id) {

        return inventoryRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Inventory item not found."));
    }

    private void saveTransaction(
            InventoryItem item,
            BigDecimal stockBefore,
            BigDecimal stockAfter,
            BigDecimal quantity,
            TransactionType transactionType,
            ReferenceType referenceType,
            Long referenceId,
            String supplierName,
            BigDecimal unitPrice,
            BigDecimal totalAmount,
            String remarks) {

        item.setAvailableQuantity(stockAfter);

        inventoryRepository.save(item);

        InventoryTransaction transaction = InventoryTransaction.builder()
                .inventoryItem(item)
                .transactionType(transactionType)
                .quantity(quantity)
                .stockBefore(stockBefore)
                .stockAfter(stockAfter)
                .referenceType(referenceType)
                .referenceId(referenceId)
                .supplierName(supplierName)
                .unitPrice(unitPrice)
                .totalAmount(totalAmount)
                .remarks(remarks)
                .build();

        transactionRepository.save(transaction);
    }

    private StockResponse toResponse(InventoryItem item) {

        return StockResponse.builder()
                .inventoryId(item.getId())
                .inventoryName(item.getName())
                .availableQuantity(item.getAvailableQuantity())
                .unit(item.getUnit())
                .lowStock(
                        item.getAvailableQuantity()
                                .compareTo(item.getMinimumQuantity()) <= 0
                )
                .build();
    }

    /**
     /**
     * Returns the quantity as entered.
     * Currently the application maintains stock only in the inventory base unit
     * (ML, GM, PCS, etc.).
     *
     * Package conversion (Bottle → ML, Box → PCS) will be introduced later
     * when Service Inventory integration is implemented.
     */
    private BigDecimal convertQuantity(
            InventoryItem item,
            BigDecimal quantity,
            QuantityMode mode) {

        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero.");
        }

        return quantity;
    }
}