package com.saloon.saloon_mobile_api.repository;

import com.saloon.saloon_mobile_api.entity.InventoryCategory;
import com.saloon.saloon_mobile_api.entity.InventoryItem;
import com.saloon.saloon_mobile_api.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository
        extends JpaRepository<InventoryItem, Long> {

    /**
     * Active Inventory
     */
    List<InventoryItem> findByActiveTrue();
    Page<InventoryItem> findByActiveTrue(Pageable pageable);

    @Query(
            value = """
                SELECT i
                FROM InventoryItem i
                WHERE i.active = true
                  AND (:lowStockOnly = false
                       OR i.availableQuantity <= i.minimumQuantity)
                  AND (
                       :search = ''
                       OR LOWER(i.name) LIKE LOWER(CONCAT('%', :search, '%'))
                       OR LOWER(COALESCE(i.brand, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                  )
                """,
            countQuery = """
                SELECT COUNT(i)
                FROM InventoryItem i
                WHERE i.active = true
                  AND (:lowStockOnly = false
                       OR i.availableQuantity <= i.minimumQuantity)
                  AND (
                       :search = ''
                       OR LOWER(i.name) LIKE LOWER(CONCAT('%', :search, '%'))
                       OR LOWER(COALESCE(i.brand, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                  )
                """
    )
    Page<InventoryItem> searchActivePage(
            @Param("search") String search,
            @Param("lowStockOnly") boolean lowStockOnly,
            Pageable pageable
    );

    /**
     * Search by Name
     */
    List<InventoryItem> findByNameContainingIgnoreCase(String name);

    /**
     * Find by Category
     */
    List<InventoryItem> findByCategoryAndActiveTrue(
            InventoryCategory category
    );

    /**
     * Find by Unit
     */
    List<InventoryItem> findByUnit(Unit unit);

    /**
     * Find by Brand
     */
    List<InventoryItem> findByBrandContainingIgnoreCase(String brand);

    /**
     * Find by Supplier
     */
    List<InventoryItem> findBySupplierNameContainingIgnoreCase(
            String supplierName
    );

    /**
     * Find by Exact Name
     */
    Optional<InventoryItem> findByNameIgnoreCase(String name);

    /**
     * Low Stock
     */
    @Query("""
        SELECT i
        FROM InventoryItem i
        WHERE i.active = true
          AND i.availableQuantity <= i.minimumQuantity
        """)
    List<InventoryItem> findLowStockItems();
    @Query(
            value = """
                SELECT i
                FROM InventoryItem i
                WHERE i.active = true
                  AND i.availableQuantity <= i.minimumQuantity
                """,
            countQuery = """
                SELECT COUNT(i)
                FROM InventoryItem i
                WHERE i.active = true
                  AND i.availableQuantity <= i.minimumQuantity
                """
    )
    Page<InventoryItem> findLowStockItems(Pageable pageable);

    /**
     * Available Quantity <= value
     */
    List<InventoryItem> findByAvailableQuantityLessThanEqual(
            BigDecimal quantity
    );

    /**
     * Dashboard Counts
     */
    long countByActiveTrue();

    long countByActiveFalse();

    @Query("""
        SELECT COUNT(i)
        FROM InventoryItem i
        WHERE i.active = true
          AND i.availableQuantity <= i.minimumQuantity
        """)
    long countLowStockItems();

    /**
     * Total Inventory Value (Purchase)
     */
    @Query("""
        SELECT COALESCE(
            SUM(i.availableQuantity * i.purchasePrice),
            0
        )
        FROM InventoryItem i
        WHERE i.active = true
        """)
    BigDecimal getTotalPurchaseStockValue();

    /**
     * Total Inventory Value (Selling)
     */
    @Query("""
        SELECT COALESCE(
            SUM(i.availableQuantity * i.sellingPrice),
            0
        )
        FROM InventoryItem i
        WHERE i.active = true
        """)
    BigDecimal getTotalSellingStockValue();
}