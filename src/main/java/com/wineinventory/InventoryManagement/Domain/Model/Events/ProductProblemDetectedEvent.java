package com.wineinventory.Domain.Model.Events;

import java.time.Instant;

public record ProductProblemDetectedEvent(
    String productId,
    String problemDescription,
    Instant occurredOn
) {
}