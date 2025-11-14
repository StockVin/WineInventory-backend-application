package com.wineinventory.reportingandcareguide.domain.services;

import com.wineinventory.reportingandcareguide.domain.model.commands.CreateCareGuideCommand;
import com.wineinventory.reportingandcareguide.domain.model.commands.CreateCareGuideWithoutProductCommand;
import com.wineinventory.reportingandcareguide.domain.model.commands.DeleteCareGuideCommand;
import com.wineinventory.reportingandcareguide.domain.model.commands.UpdateCareGuideCommand;
import com.wineinventory.reportingandcareguide.domain.model.entities.CareGuide;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @name CareGuideCommandService
 * @summary
 * This interface represents the service to handle care guide commands.
 */
public interface CareGuideCommandService {
    /**
     * Handles the create care guide command.
     * @param command The create care guide command.
     * @return The created care guide.
     *
     * @throws IllegalArgumentException If guideName, type or description is null or empty
     * @see CreateCareGuideCommand
     */
    Optional<CareGuide> handle(CreateCareGuideCommand command);

    @Transactional
    Optional<CareGuide> handle(UpdateCareGuideCommand command);

    /**
     * Handles the delete care guide command.
     * @param command The delete care guide command.
     *
     * @throws IllegalArgumentException If id is null or less than or equal to 0
     * @see DeleteCareGuideCommand
     */
    void handle(DeleteCareGuideCommand command);
    Optional<CareGuide> handle(CreateCareGuideWithoutProductCommand command);
}