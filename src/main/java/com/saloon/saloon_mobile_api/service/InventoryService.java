package com.saloon.saloon_mobile_api.service;

import com.saloon.saloon_mobile_api.dto.CreateInventoryRequest;
import com.saloon.saloon_mobile_api.dto.InventoryResponse;
import com.saloon.saloon_mobile_api.dto.UpdateInventoryRequest;

import java.util.List;

public interface InventoryService {

    InventoryResponse create(CreateInventoryRequest request);

    InventoryResponse update(Long id,
                             UpdateInventoryRequest request) throws Exception;

    InventoryResponse getById(Long id) throws Exception;

    List<InventoryResponse> getAll();

    List<InventoryResponse> getLowStock();

    void delete(Long id) throws Exception;

}