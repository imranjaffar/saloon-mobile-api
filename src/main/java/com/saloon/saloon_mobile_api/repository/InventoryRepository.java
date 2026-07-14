package com.saloon.saloon_mobile_api.repository;




import com.saloon.saloon_mobile_api.entity.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;



public interface InventoryRepository
        extends JpaRepository<InventoryItem,Long>{



    List<InventoryItem>
    findByQuantityLessThan(
            Integer qty
    );


}
