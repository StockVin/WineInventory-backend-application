package com.wineinventory.Authorization.Interfaces.REST.Resources;

import com.wineinventory.Authorization.Domain.Model.ValueObjects.UserRoles;

public record SignUpResource(String username, String email, String password, String validationPassword, UserRoles role) {}
