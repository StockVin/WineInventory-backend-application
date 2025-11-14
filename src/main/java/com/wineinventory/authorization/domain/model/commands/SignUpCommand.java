package com.wineinventory.authorization.domain.model.commands;

import com.wineinventory.authorization.domain.model.valueobjects.UserRoles;

public record SignUpCommand(String username, String email, String password, String validationPassword, UserRoles role) {}
