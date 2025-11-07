package com.wineinventory.InventoryManagement.Domain.Model.Queries;


import com.wineinventory.InventoryManagement.Domain.Model.ValueObjects.AccountId;

/**
 * This query is used to retrieve all the inventories of a specific account ID.
 * @param accountId The account ID used to retrieve all the warehouses.
 */
public record GetAllWarehousesByAccountIdQuery(AccountId accountId) {
}