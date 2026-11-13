package com.saloon.saloon_mobile_api.service;

import com.saloon.saloon_mobile_api.dto.*;
import com.saloon.saloon_mobile_api.entity.*;
import com.saloon.saloon_mobile_api.mapper.InventoryTransactionMapper;
import com.saloon.saloon_mobile_api.repository.ExpenseRepository;
import com.saloon.saloon_mobile_api.repository.InventoryRepository;
import com.saloon.saloon_mobile_api.repository.InventoryTransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StockService {

    private final InventoryRepository inventoryRepository;
    private final InventoryTransactionRepository transactionRepository;
    private final InventoryTransactionMapper transactionMapper;
    private final ExpenseRepository expenseRepo;
    private final FinanceService financeService;

    public StockResponse stockIn(StockInRequest request) {
        InventoryItem item = getItem(request.getInventoryId());

        BigDecimal stockBefore = item.getAvailableQuantity() == null ? BigDecimal.ZERO : item.getAvailableQuantity();
        BigDecimal convertedQuantity = convertQuantity(item, request.getQuantity(), request.getQuantityMode(), false);

        BigDecimal stockAfter = stockBefore.add(convertedQuantity);

        BigDecimal totalAmount = request.getUnitPrice()
                .multiply(request.getQuantity());

        item.setAvailableQuantity(stockAfter);
        inventoryRepository.save(item);

        InventoryTransaction transaction = saveTransaction(
                item,
                stockBefore,
                stockAfter,
                convertedQuantity,
                TransactionType.STOCK_IN,
                request.getReferenceType(),
                request.getReferenceId(),
                request.getSupplierName(),
                request.getUnitPrice(),
                totalAmount,
                request.getRemarks()
        );

        financeService.createStockInExpense(transaction, request);

        return toResponse(item);
    }

    public StockResponse stockOut(StockOutRequest request) {
        InventoryItem item = getItem(request.getInventoryId());

        BigDecimal stockBefore = item.getAvailableQuantity() == null ? BigDecimal.ZERO : item.getAvailableQuantity();
        BigDecimal convertedQuantity = convertQuantity(item, request.getQuantity(), request.getQuantityMode(), false);

        if (stockBefore.compareTo(convertedQuantity) < 0) {
            throw new IllegalArgumentException("Insufficient stock.");
        }

        BigDecimal stockAfter = stockBefore.subtract(convertedQuantity);

        saveTransaction(
                item,
                stockBefore,
                stockAfter,
                convertedQuantity.negate(),
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

    public StockResponse adjustStock(StockAdjustmentRequest request) {
        InventoryItem item = getItem(request.getInventoryId());

        BigDecimal stockBefore = item.getAvailableQuantity() == null ? BigDecimal.ZERO : item.getAvailableQuantity();
        BigDecimal newBaseQuantity = convertQuantity(item, request.getNewQuantity(), request.getQuantityMode(), true);

        BigDecimal difference = newBaseQuantity.subtract(stockBefore);

        saveTransaction(
                item,
                stockBefore,
                newBaseQuantity,
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

    public StockResponse deductStock(StockDeductionRequest request) {
        InventoryItem item = getItem(request.getInventoryId());

        BigDecimal stockBefore = item.getAvailableQuantity() == null ? BigDecimal.ZERO : item.getAvailableQuantity();
        BigDecimal deduction = convertQuantity(item, request.getQuantity(), request.getQuantityMode(), false);

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
        return transactionRepository.findByInventoryItemIdOrderByCreatedAtDesc(inventoryId)
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

    private InventoryItem getItem(Long id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inventory item not found."));
    }

    private InventoryTransaction saveTransaction(
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
            String remarks
    ) {
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

        return transactionRepository.save(transaction);
    }

    private StockResponse toResponse(InventoryItem item) {
        return StockResponse.builder()
                .inventoryId(item.getId())
                .inventoryName(item.getName())
                .availableQuantity(item.getAvailableQuantity() == null ? BigDecimal.ZERO : item.getAvailableQuantity())
                .unit(item.getUnit())
                .lowStock(item.getAvailableQuantity() != null
                        && item.getMinimumQuantity() != null
                        && item.getAvailableQuantity().compareTo(item.getMinimumQuantity()) <= 0)
                .build();
    }

    private BigDecimal convertQuantity(
            InventoryItem item,
            BigDecimal quantity,
            QuantityMode mode,
            boolean allowZero
    ) {
        if (quantity == null) {
            throw new IllegalArgumentException("Quantity is required.");
        }
        if (quantity.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        if (!allowZero && quantity.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }

        if (mode == null) {
            throw new IllegalArgumentException("Quantity mode is required.");
        }

        if (mode == QuantityMode.UNIT) {
            return quantity;
        }

        if (mode == QuantityMode.PACKAGE) {
            if (!item.hasPackageConfiguration()) {
                throw new IllegalArgumentException("PACKAGE mode requires packageName and unitsPerPackage.");
            }
            return quantity.multiply(item.getUnitsPerPackage());
        }

        throw new IllegalArgumentException("Unsupported quantity mode: " + mode);
    }
}
