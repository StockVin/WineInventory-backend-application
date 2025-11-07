package com.wineinventory.ReportingAndCareGuide.Domain.Model.Queries;

import com.wineinventory.InventoryManagement.Domain.Model.Aggregates.Product;
import com.wineinventory.InventoryManagement.Domain.Model.Aggregates.Warehouse;

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