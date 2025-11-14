package com.wineinventory.authorization.domain.model.commands;

import com.wineinventory.authorization.domain.model.valueobjects.UserRoles;

public record CreateUserCommand(String username, String password, String validationPassword, UserRoles role) {
}
