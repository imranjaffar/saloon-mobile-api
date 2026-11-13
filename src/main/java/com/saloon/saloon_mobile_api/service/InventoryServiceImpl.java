package com.saloon.saloon_mobile_api.service;

import com.saloon.saloon_mobile_api.dto.CreateInventoryRequest;
import com.saloon.saloon_mobile_api.dto.InventoryResponse;
import com.saloon.saloon_mobile_api.dto.UpdateInventoryRequest;
import com.saloon.saloon_mobile_api.entity.InventoryItem;
import com.saloon.saloon_mobile_api.mapper.InventoryMapper;
import com.saloon.saloon_mobile_api.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    @Override
    public InventoryResponse create(CreateInventoryRequest request) {
        if (inventoryRepository.findByNameIgnoreCase(request.getName()).isPresent()) {
            throw new RuntimeException("Inventory item already exists.");
        }

        String normalizedPackageName = normalizePackageName(request.getPackageName());
        validatePackageConfig(normalizedPackageName, request.getUnitsPerPackage());

        InventoryItem item = InventoryItem.builder()
                .name(request.getName())
                .category(request.getCategory())
                .brand(request.getBrand())
                .unit(request.getUnit())
                .availableQuantity(BigDecimal.ZERO)
                .minimumQuantity(request.getMinimumQuantity())
                .purchasePrice(request.getPurchasePrice())
                .sellingPrice(request.getSellingPrice())
                .supplierName(request.getSupplierName())
                .remarks(request.getRemarks())
                .packageName(normalizedPackageName)
                .unitsPerPackage(request.getUnitsPerPackage())
                .active(true)
                .build();

        return inventoryMapper.toResponse(inventoryRepository.save(item));
    }

//    private static InventoryItem getInventoryItem(CreateInventoryRequest request) {
//        InventoryItem item = new InventoryItem();
//
//        item.setName(request.getName());
//        item.setUnit(request.getUnit());
//
//        // Stock starts from zero. Stock changes should go through StockService.
//        item.setCurrentStock(BigDecimal.ZERO);
//
//        item.setMinimumStock(request.getReorderLevel());
//        item.setTrackingType(request.getTrackingType());
//        item.setCategory(request.getCategory());
//        item.setPackageQuantity(request.getPackageQuantity());
//        item.setPackageUnit(request.getPackageUnit());
//        item.setActive(true);
//        return item;
//    }

    @Override
    public InventoryResponse update(Long id, UpdateInventoryRequest request) {
        InventoryItem item = getEntity(id);

        if (request.getName() != null) item.setName(request.getName());
        if (request.getCategory() != null) item.setCategory(request.getCategory());
        if (request.getBrand() != null) item.setBrand(request.getBrand());
        if (request.getUnit() != null) item.setUnit(request.getUnit());
        if (request.getMinimumQuantity() != null) item.setMinimumQuantity(request.getMinimumQuantity());
        if (request.getPurchasePrice() != null) item.setPurchasePrice(request.getPurchasePrice());
        if (request.getSellingPrice() != null) item.setSellingPrice(request.getSellingPrice());
        if (request.getSupplierName() != null) item.setSupplierName(request.getSupplierName());
        if (request.getRemarks() != null) item.setRemarks(request.getRemarks());
        if (request.getActive() != null) item.setActive(request.getActive());

        String normalizedPackageName = normalizePackageName(request.getPackageName());
        if (request.getPackageName() != null || request.getUnitsPerPackage() != null) {
            validatePackageConfig(normalizedPackageName, request.getUnitsPerPackage());
            item.setPackageName(normalizedPackageName);
            item.setUnitsPerPackage(request.getUnitsPerPackage());
        } else if (request.getPackageName() == null && request.getUnitsPerPackage() == null) {
            // keep legacy/unchanged values
        }

        return inventoryMapper.toResponse(inventoryRepository.save(item));
    }

    private void validatePackageConfig(String packageName, BigDecimal unitsPerPackage) {
        boolean hasPackageName = packageName != null && !packageName.trim().isEmpty();
        boolean hasUnitsPerPackage = unitsPerPackage != null;

        if (!hasPackageName && !hasUnitsPerPackage) {
            return;
        }

        if (!hasPackageName || !hasUnitsPerPackage || unitsPerPackage.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Package name and unitsPerPackage must be supplied together, and unitsPerPackage must be greater than zero.");
        }
    }

    private String normalizePackageName(String packageName) {
        if (packageName == null) return null;
        String normalized = packageName.trim();
        return normalized.isEmpty() ? null : normalized;
    }

    @Override
    @Transactional(readOnly = true)
    public InventoryResponse getById(Long id) throws Exception {
        return inventoryMapper.toResponse(getEntity(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponse> getAll() {
        return inventoryMapper.toResponseList(inventoryRepository.findByActiveTrue());
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponse> getPage(Pageable pageable) {
        return inventoryRepository.findByActiveTrue(pageable)
                .map(inventoryMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponse> getPage(
            Pageable pageable,
            String search,
            boolean lowStockOnly
    ) {
        String query = search == null ? "" : search.trim();
        return inventoryRepository.searchActivePage(query, lowStockOnly, pageable)
                .map(inventoryMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponse> getLowStock() {
        return inventoryMapper.toResponseList(inventoryRepository.findLowStockItems());
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponse> getLowStock(Pageable pageable) {
        return inventoryRepository.findLowStockItems(pageable)
                .map(inventoryMapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {
        InventoryItem item = getEntity(id);
        item.setActive(false);
        inventoryRepository.save(item);
    }

    private InventoryItem getEntity(Long id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory Item not found."));
    }
}
