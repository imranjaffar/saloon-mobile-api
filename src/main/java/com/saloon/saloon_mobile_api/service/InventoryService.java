package com.saloon.saloon_mobile_api.service;




import com.saloon.saloon_mobile_api.dto.InventoryRequest;
import com.saloon.saloon_mobile_api.entity.InventoryItem;

import java.util.List;



public interface InventoryService {


    InventoryItem add(
            InventoryRequest request
    );


    InventoryItem update(
            Long id,
            InventoryRequest request
    );


    List<InventoryItem> getAll();


    List<InventoryItem> lowStock();


    void delete(Long id);



}
