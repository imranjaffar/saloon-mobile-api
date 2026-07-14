package com.saloon.saloon_mobile_api.service;


import com.saloon.saloon_mobile_api.dto.InventoryRequest;
import com.saloon.saloon_mobile_api.entity.InventoryItem;
import com.saloon.saloon_mobile_api.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


import java.util.List;



@Service
@RequiredArgsConstructor
public class InventoryServiceImpl
        implements InventoryService {



    private final InventoryRepository repo;




    @Override
    public InventoryItem add(
            InventoryRequest r){


        InventoryItem item =
                InventoryItem.builder()

                        .name(r.getName())

                        .category(
                                r.getCategory()
                        )

                        .quantity(
                                r.getQuantity()
                        )

                        .minimumStock(
                                r.getMinimumStock()
                        )

                        .purchasePrice(
                                r.getPurchasePrice()
                        )

                        .sellingPrice(
                                r.getSellingPrice()
                        )

                        .build();



        return repo.save(item);

    }




    @Override
    public InventoryItem update(
            Long id,
            InventoryRequest r){


        InventoryItem item =
                repo.findById(id)
                        .orElseThrow();


        item.setName(r.getName());

        item.setCategory(
                r.getCategory()
        );

        item.setQuantity(
                r.getQuantity()
        );


        return repo.save(item);

    }




    @Override
    public List<InventoryItem> getAll(){

        return repo.findAll();

    }




    @Override
    public List<InventoryItem> lowStock(){


        return repo.findByQuantityLessThan(
                5
        );

    }





    @Override
    public void delete(Long id){

        repo.deleteById(id);

    }


}
