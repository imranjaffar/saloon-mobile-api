package com.saloon.saloon_mobile_api.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockDashboardResponse {
    private long totalItems;
    private long activeItems;
    private long inactiveItems;
    private long lowStockItems;
    private long totalTransactions;
}
