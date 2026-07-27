package com.saloon.saloon_mobile_api.repository;

import com.saloon.saloon_mobile_api.entity.InventoryTransaction;
import com.saloon.saloon_mobile_api.entity.ReferenceType;
import com.saloon.saloon_mobile_api.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InventoryTransactionRepository
        extends JpaRepository<InventoryTransaction, Long> {

    /**
     * All transactions for an inventory item
     */
    List<InventoryTransaction> findByInventoryItemId(
            Long inventoryItemId
    );

    /**
     * Stock ledger
     */
    List<InventoryTransaction> findByInventoryItemIdOrderByCreatedAtDesc(
            Long inventoryItemId
    );

    /**
     * Transactions by type
     */
    List<InventoryTransaction> findByTransactionType(
            TransactionType transactionType
    );

    /**
     * Transactions by reference type
     */
    List<InventoryTransaction> findByReferenceType(
            ReferenceType referenceType
    );

    /**
     * Appointment deductions
     */
    List<InventoryTransaction> findByReferenceTypeAndReferenceId(
            ReferenceType referenceType,
            Long referenceId
    );

    /**
     * Transactions within a date range
     */
    List<InventoryTransaction> findByCreatedAtBetween(
            LocalDateTime from,
            LocalDateTime to
    );

    /**
     * Transactions of an inventory item within a date range
     */
    List<InventoryTransaction> findByInventoryItemIdAndCreatedAtBetween(
            Long inventoryItemId,
            LocalDateTime from,
            LocalDateTime to
    );

    /**
     * Latest transaction for an inventory item
     */
    InventoryTransaction findTopByInventoryItemIdOrderByCreatedAtDesc(
            Long inventoryItemId
    );

    /**
     * Purchase history by supplier
     */
    List<InventoryTransaction> findBySupplierNameContainingIgnoreCaseOrderByCreatedAtDesc(
            String supplierName
    );

    /**
     * Purchase history only
     */
    List<InventoryTransaction> findByTransactionTypeOrderByCreatedAtDesc(
            TransactionType transactionType
    );

}