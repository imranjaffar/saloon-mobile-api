package com.saloon.saloon_mobile_api.controller;


import com.saloon.saloon_mobile_api.dto.InventoryRequest;
import com.saloon.saloon_mobile_api.entity.InventoryItem;
import com.saloon.saloon_mobile_api.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
@CrossOrigin("*")
public class InventoryController {



    private final InventoryService service;




    @PostMapping
    public InventoryItem add(
            @RequestBody InventoryRequest request){

        return service.add(request);

    }





    @GetMapping
    public List<InventoryItem> all(){

        return service.getAll();

    }





    @GetMapping("/low-stock")
    public List<InventoryItem> low(){

        return service.lowStock();

    }





    @PutMapping("/{id}")
    public InventoryItem update(
            @PathVariable Long id,
            @RequestBody InventoryRequest request){

        return service.update(id,request);

    }





    @DeleteMapping("/{id}")
    public String delete(
            @PathVariable Long id){

        service.delete(id);

        return "Deleted";

    }


}
