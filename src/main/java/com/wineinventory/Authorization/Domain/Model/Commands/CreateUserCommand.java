package com.wineinventory.Authorization.Domain.Model.Commands;

import com.wineinventory.Authorization.Domain.Model.ValueObjects.UserRoles;

public record CreateUserCommand(String username, String password, String validationPassword, UserRoles role) {
}
