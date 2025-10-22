package com.wineinventory.Authorization.Domain.Model.Commands;

import com.wineinventory.Authorization.Domain.Model.ValueObjects.UserRoles;

public record SignUpCommand(String username, String email, String password, String validationPassword, UserRoles role) {}
