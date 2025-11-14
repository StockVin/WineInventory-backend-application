package com.wineinventory.profilemanagement.domain.services;

import com.wineinventory.authorization.domain.model.aggregates.User;
import com.wineinventory.profilemanagement.domain.model.commands.UpdateProfileCommand;

public interface ProfileCommandService {
    User handle(UpdateProfileCommand command);
}
