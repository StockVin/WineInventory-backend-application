package com.wineinventory.api.rest;

import com.wineinventory.InventoryManagement.Application.Queries.Dtos.InventoryItemDto;


import com.wineinventory.Application.Commands.CreateProductCommand;
import com.wineinventory.Application.Commands.DeleteProductCommand;
import com.wineinventory.Application.Queries.GetAllProductsByAccountIdQuery;
import com.wineinventory.Application.Services.ProductCommandService;
import com.wineinventory.InventoryManagement.Application.Services.ProductQueryService;
import com.wineinventory.Domain.Exceptions.ProductFailedCreationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    private final ProductCommandService commandService;
    private final ProductQueryService queryService;
    
    private static final Long DEFAULT_ACCOUNT_ID = 1L; 

    public InventoryController(ProductCommandService commandService, ProductQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    // COMMAND: Crear un nuevo producto
    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody CreateProductCommand request) {
        try {
            CreateProductCommand command = new CreateProductCommand(
                request.name(), request.type(), request.price(), request.expirationDate(), 
                request.currentStock(), request.minStockLevel(), request.location(), 
                request.imageUrl(), DEFAULT_ACCOUNT_ID 
            );
            
            String newProductId = commandService.handle(command);
            
            return ResponseEntity.created(URI.create("/api/v1/inventory/" + newProductId)).body(newProductId);

        } catch (ProductFailedCreationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // COMMAND: Eliminar un producto
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String productId) {
        DeleteProductCommand command = new DeleteProductCommand(productId, DEFAULT_ACCOUNT_ID);
        commandService.handle(command);
        
        return ResponseEntity.noContent().build();
    }
    
    // QUERY: Obtener la lista de inventario
    @GetMapping
    public ResponseEntity<List<InventoryItemDto>> getInventoryList() {
        GetAllProductsByAccountIdQuery query = new GetAllProductsByAccountIdQuery(DEFAULT_ACCOUNT_ID);
        List<InventoryItemDto> products = queryService.handle(query);
        
        return ResponseEntity.ok(products);
    }
}