package com.wineinventory.authorization.interfaces.acl;

public interface AuthenticationContextFacade {

    Long createUser(String username, String email, String password);
}