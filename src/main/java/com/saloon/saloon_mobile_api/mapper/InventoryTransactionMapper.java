package com.saloon.saloon_mobile_api.mapper;

import com.saloon.saloon_mobile_api.dto.InventoryTransactionResponse;
import com.saloon.saloon_mobile_api.entity.InventoryTransaction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InventoryTransactionMapper {

    public InventoryTransactionResponse toResponse(InventoryTransaction transaction) {

        if (transaction == null) {
            return null;
        }

        InventoryTransactionResponse response = new InventoryTransactionResponse();

        response.setId(transaction.getId());

        response.setInventoryId(transaction.getInventoryItem().getId());

        response.setInventoryName(transaction.getInventoryItem().getName());

        response.setTransactionType(transaction.getTransactionType());

        response.setQuantity(transaction.getQuantity());

        response.setStockBefore(transaction.getStockBefore());

        response.setStockAfter(transaction.getStockAfter());

        response.setUnitPrice(transaction.getUnitPrice());

        response.setTotalAmount(transaction.getTotalAmount());

        response.setSupplierName(transaction.getSupplierName());

        response.setReferenceType(transaction.getReferenceType());

        response.setReferenceId(transaction.getReferenceId());

        response.setRemarks(transaction.getRemarks());

        response.setCreatedAt(transaction.getCreatedAt());

        return response;
    }

    public List<InventoryTransactionResponse> toResponseList(
            List<InventoryTransaction> transactions) {

        return transactions
                .stream()
                .map(this::toResponse)
                .toList();
    }
}