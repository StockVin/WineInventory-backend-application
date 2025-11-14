package com.wineinventory.authorization.interfaces.rest.resources;

import com.wineinventory.authorization.domain.model.valueobjects.UserRoles;

public record SignUpResource(String username, String email, String password, String validationPassword, UserRoles role) {}
