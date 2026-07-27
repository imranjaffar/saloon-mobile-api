package com.saloon.saloon_mobile_api.service;

import com.saloon.saloon_mobile_api.dto.CreateInventoryRequest;
import com.saloon.saloon_mobile_api.dto.InventoryResponse;
import com.saloon.saloon_mobile_api.dto.UpdateInventoryRequest;
import com.saloon.saloon_mobile_api.entity.InventoryItem;
import com.saloon.saloon_mobile_api.entity.PackageUnit;
import com.saloon.saloon_mobile_api.entity.Unit;
import com.saloon.saloon_mobile_api.mapper.InventoryMapper;
import com.saloon.saloon_mobile_api.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static com.saloon.saloon_mobile_api.entity.PackageUnit.*;

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
                .active(true)
                .build();

        return inventoryMapper.toResponse(
                inventoryRepository.save(item)
        );
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
    public InventoryResponse update(
            Long id,
            UpdateInventoryRequest request
    ) {

        InventoryItem item = getEntity(id);

        item.setName(request.getName());
        item.setCategory(request.getCategory());
        item.setBrand(request.getBrand());
        item.setUnit(request.getUnit());
        item.setMinimumQuantity(request.getMinimumQuantity());
        item.setPurchasePrice(request.getPurchasePrice());
        item.setSellingPrice(request.getSellingPrice());
        item.setSupplierName(request.getSupplierName());
        item.setRemarks(request.getRemarks());
        item.setActive(request.getActive());

        return inventoryMapper.toResponse(
                inventoryRepository.save(item)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public InventoryResponse getById(Long id) throws Exception {
        return inventoryMapper.toResponse(getEntity(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponse> getAll() {

        return inventoryMapper.toResponseList(
                inventoryRepository.findByActiveTrue()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponse> getLowStock() {

        return inventoryMapper.toResponseList(
                inventoryRepository.findLowStockItems()
        );
    }

    @Override
    public void delete(Long id) {

        InventoryItem item = getEntity(id);

        item.setActive(false);

        inventoryRepository.save(item);
    }

    private InventoryItem getEntity(Long id) {

        return inventoryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Inventory Item not found."));
    }


//
//    private void validatePackage(PackageUnit packageUnit,BigDecimal packageQuantity,Unit unit) {
//
//        if (packageUnit == null &&
//                packageQuantity == null) {
//            return; // inventory without package tracking
//        }
//
//        if (packageUnit == null ||
//                packageQuantity == null) {
//            throw new IllegalArgumentException(
//                    "Both package unit and package quantity are required.");
//        }
//
//        switch (unit) {
//
//            case ML, LITRE -> {
//                if (packageUnit != PackageUnit.BOTTLE &&
//                        packageUnit != PackageUnit.CAN &&
//                        packageUnit != CARTON) {
//                    throw new IllegalArgumentException(
//                            "Liquids can only use Bottle, Can or Carton as package unit.");
//                }
//            }
//
//            case KG, GM -> {
//                if (packageUnit != BAG &&
//                        packageUnit != BOX) {
//                    throw new IllegalArgumentException(
//                            "Weight items can only use Bag or Box as package unit.");
//                }
//            }
//
//            case PCS -> {
//                if (packageUnit != BOX &&
//                        packageUnit != CARTON) {
//                    throw new IllegalArgumentException(
//                            "Pieces can only use Box or Carton as package unit.");
//                }
//            }
//
//
//            default -> {
//            }
//        }
//    }
}