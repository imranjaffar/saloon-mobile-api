package com.saloon.saloon_mobile_api.dto;

import com.saloon.saloon_mobile_api.entity.InventoryTransaction;
import com.saloon.saloon_mobile_api.entity.ReferenceType;
import com.saloon.saloon_mobile_api.entity.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryTransactionResponse {

    private Long id;

    private Long inventoryId;

    private String inventoryName;

    private TransactionType transactionType;

    private BigDecimal quantity;

    private BigDecimal stockBefore;

    private BigDecimal stockAfter;

    private BigDecimal unitPrice;

    private BigDecimal totalAmount;

    private String supplierName;

    private ReferenceType referenceType;

    private Long referenceId;

    private String remarks;

    private LocalDateTime createdAt;
}