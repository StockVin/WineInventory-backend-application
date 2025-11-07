package com.wineinventory.Authorization.Domain.Services;

import com.wineinventory.Authorization.Domain.Model.Aggregates.User;
import com.wineinventory.Authorization.Domain.Model.Queries.GetUserByEmailQuery;
import com.wineinventory.Authorization.Domain.Model.Queries.GetUserByIdQuery;
import java.util.Optional;

/**
 * User query service
 * This interface represents the service to handle user queries.
 */
public interface UserQueryService {

    /**
     * Handle get user by id query
     * @param query the {@link GetUserByIdQuery} query
     * @return an {@link Optional} of {@link User} entity
     */ 
    Optional<User> handle(GetUserByIdQuery query);

    /**
     * Handle get user by email query
     * @param query the {@link GetUserByEmailQuery} query
     * @return an {@link Optional} of {@link User} entity
     */
    Optional<User> handle(GetUserByEmailQuery query);
}