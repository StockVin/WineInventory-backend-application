package com.wineinventory.ReportingAndCareGuide.Interfaces.REST.Controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(value = "/api/v1/careguides", produces = APPLICATION_JSON_VALUE)
@Tag(name = "CareGuides",description = "Available Care Guide Endpoints.")
public class CareGuideController {
}
