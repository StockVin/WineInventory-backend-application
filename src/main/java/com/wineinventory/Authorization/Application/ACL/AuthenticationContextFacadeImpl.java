package com.wineinventory.Authorization.Application.ACL;

import com.wineinventory.Authorization.Domain.Model.Commands.SignUpCommand;
import com.wineinventory.Authorization.Domain.Model.ValueObjects.UserRoles;
import com.wineinventory.Authorization.Domain.Services.UserCommandService;
import com.wineinventory.Authorization.Interfaces.ACL.AuthenticationContextFacade;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationContextFacadeImpl implements AuthenticationContextFacade {

    private final UserCommandService userCommandService;

    public AuthenticationContextFacadeImpl(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    @Override
    public Long createUser(String username, String email, String password) {

        var signupCommand = new SignUpCommand(
                username,
                email,
                password,
                password,
                UserRoles.PRODUCER
        );

        var user = userCommandService.handle(signupCommand);
        return user.isPresent() ? user.get().getUserId() : Long.valueOf(0L);
    }
}