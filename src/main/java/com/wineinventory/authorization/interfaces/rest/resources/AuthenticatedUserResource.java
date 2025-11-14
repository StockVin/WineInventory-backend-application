package com.wineinventory.authorization.interfaces.rest.resources;

public record AuthenticatedUserResource(Long userId, String username, String token, Long accountId) {}