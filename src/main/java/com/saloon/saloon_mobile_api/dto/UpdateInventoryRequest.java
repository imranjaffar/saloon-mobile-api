package com.saloon.saloon_mobile_api.dto;

import com.saloon.saloon_mobile_api.entity.InventoryCategory;
import com.saloon.saloon_mobile_api.entity.Unit;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateInventoryRequest {

    @Size(max = 100, message = "Name cannot exceed 100 characters.")
    private String name;

    private InventoryCategory category;

    @Size(max = 100, message = "Brand cannot exceed 100 characters.")
    private String brand;

    private Unit unit;

    @PositiveOrZero(message = "Minimum quantity cannot be negative.")
    private BigDecimal minimumQuantity;

    @PositiveOrZero(message = "Purchase price cannot be negative.")
    private BigDecimal purchasePrice;

    @PositiveOrZero(message = "Selling price cannot be negative.")
    private BigDecimal sellingPrice;

    @Size(max = 100, message = "Supplier name cannot exceed 100 characters.")
    private String supplierName;

    @Size(max = 500, message = "Remarks cannot exceed 500 characters.")
    private String remarks;

    private Boolean active;

    @Size(max = 100, message = "Package name cannot exceed 100 characters.")
    private String packageName;

    @Positive(message = "Units per package must be greater than zero.")
    private BigDecimal unitsPerPackage;

    @AssertTrue(message = "Package name and unitsPerPackage must be supplied together, and unitsPerPackage must be greater than zero.")
    public boolean isPackageConfigurationValid() {
        boolean hasPackageName = packageName != null && !packageName.trim().isEmpty();
        boolean hasUnitsPerPackage = unitsPerPackage != null;

        if (!hasPackageName && !hasUnitsPerPackage) {
            return true;
        }

        return hasPackageName && hasUnitsPerPackage && unitsPerPackage.compareTo(BigDecimal.ZERO) > 0;
    }

}