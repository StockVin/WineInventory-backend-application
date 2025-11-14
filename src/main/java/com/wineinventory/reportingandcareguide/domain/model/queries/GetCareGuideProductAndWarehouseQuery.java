package com.wineinventory.reportingandcareguide.domain.model.queries;

import com.wineinventory.inventorymanagement.domain.model.aggregates.Product;
import com.wineinventory.inventorymanagement.domain.model.aggregates.Warehouse;

public record GetCareGuideProductAndWarehouseQuery(Long careGuideId, Product product, Warehouse warehouse) {
    public GetCareGuideProductAndWarehouseQuery{
        if(careGuideId == null){
            throw new IllegalArgumentException("Care guide ID cannot be null");
        }
        if(product == null){
            throw new IllegalArgumentException("Product cannot be null");
        }
        if(warehouse == null){
            throw new IllegalArgumentException("Warehouse cannot be null");
        }
    }
}