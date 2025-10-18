package com.wineinventory.Authorization.Interfaces.REST.Resources;

public record AuthenticatedUserResource(Long userId, String username, String token, Long accountId) {}