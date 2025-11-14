package com.wineinventory.authorization.domain.model.events;

import com.wineinventory.authorization.domain.model.valueobjects.UserRoles;

public class AccountOwnerUserCreatedEvent {
    private String username;
    private String email;
    private String password;
    private UserRoles role;

    public AccountOwnerUserCreatedEvent(String username, String email, String password, UserRoles role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
