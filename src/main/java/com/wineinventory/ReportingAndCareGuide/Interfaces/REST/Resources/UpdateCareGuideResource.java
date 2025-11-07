package com.wineinventory.ReportingAndCareGuide.Interfaces.REST.Resources;

public record UpdateCareGuideResource(
        String guideName,
        String type,
        String description,
        String imageUrl
) {
}