package com.wineinventory.ProfileManagement.Domain.Services;

import com.wineinventory.Authorization.Domain.Model.Aggregates.User;
import com.wineinventory.ProfileManagement.Domain.Model.Commands.UpdateProfileCommand;

public interface ProfileCommandService {
    User handle(UpdateProfileCommand command);
}
