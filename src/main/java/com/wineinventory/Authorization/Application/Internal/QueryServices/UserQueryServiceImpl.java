package com.wineinventory.Authorization.Application.Internal.QueryServices;

import com.wineinventory.Authorization.Domain.Model.Aggregates.User;
import com.wineinventory.Authorization.Domain.Model.Queries.GetUserByEmailQuery;
import com.wineinventory.Authorization.Domain.Model.Queries.GetUserByIdQuery;
import com.wineinventory.Authorization.Domain.Services.UserQueryService;
import com.wineinventory.Authorization.Infrastructure.Persistence.JPA.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;

    public UserQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> handle(GetUserByIdQuery query) {
        return userRepository.findById(query.userId());
    }

    @Override
    public Optional<User> handle(GetUserByEmailQuery query) {
        return userRepository.findByUsername(null);
    }
}