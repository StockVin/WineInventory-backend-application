package com.wineinventory.authorization.application.acl;

import com.wineinventory.authorization.domain.model.commands.SignUpCommand;
import com.wineinventory.authorization.domain.model.valueobjects.UserRoles;
import com.wineinventory.authorization.domain.services.UserCommandService;
import com.wineinventory.authorization.interfaces.acl.AuthenticationContextFacade;
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