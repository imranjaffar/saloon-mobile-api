package com.saloon.saloon_mobile_api.mapper;

import com.saloon.saloon_mobile_api.dto.InventoryResponse;
import com.saloon.saloon_mobile_api.entity.InventoryItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class InventoryMapper {

    public InventoryResponse toResponse(InventoryItem item) {

        if (item == null) {
            return null;
        }

        InventoryResponse response = new InventoryResponse();

        response.setId(item.getId());
        response.setName(item.getName());
        response.setCategory(item.getCategory());
        response.setBrand(item.getBrand());

        response.setAvailableQuantity(
                item.getAvailableQuantity() == null
                        ? BigDecimal.ZERO
                        : item.getAvailableQuantity()
        );

        response.setMinimumQuantity(
                item.getMinimumQuantity() == null
                        ? BigDecimal.ZERO
                        : item.getMinimumQuantity()
        );

        response.setUnit(item.getUnit());

        response.setPurchasePrice(item.getPurchasePrice());

        response.setSellingPrice(item.getSellingPrice());

        response.setSupplierName(item.getSupplierName());

        response.setRemarks(item.getRemarks());

        response.setActive(Boolean.TRUE.equals(item.getActive()));

        response.setLowStock(
                item.getAvailableQuantity()
                        .compareTo(item.getMinimumQuantity()) <= 0
        );

        response.setCreatedAt(item.getCreatedAt());

        response.setUpdatedAt(item.getUpdatedAt());

        return response;
    }

    public List<InventoryResponse> toResponseList(
            List<InventoryItem> items
    ) {
        return items.stream()
                .map(this::toResponse)
                .toList();
    }
}