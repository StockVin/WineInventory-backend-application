package com.wineinventory.inventorymanagement.domain.model.queries;

/**
 * GetAllProductsByProfileIdQuery
 *
 * @summary
 * GetAllProductsByProfileIdQuery is a query to retrieve all the products owned by a specific profile.
 *
 * @param accountId The unique identifier of the profile whose products will be retrieved.
 */
public record GetAllProductsByAccountIdQuery(Long accountId) {

    /**
     * Validates the command parameters
     */
    public GetAllProductsByAccountIdQuery {
        if (accountId == null) {
            throw new IllegalArgumentException("Profile Id cannot be null");
        }
    }
}
