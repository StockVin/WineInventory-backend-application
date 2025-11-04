package com.wineinventory.Authorization.Interfaces.ACL;

public interface AuthenticationContextFacade {

    Long createUser(String username, String email, String password);
}