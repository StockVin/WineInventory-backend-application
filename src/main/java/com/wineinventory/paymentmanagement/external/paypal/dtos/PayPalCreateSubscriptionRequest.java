package com.wineinventory.paymentmanagement.external.paypal.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PayPalCreateSubscriptionRequest(

        @JsonProperty("plan_id")
        String planId,

        @JsonProperty("start_time")
        String startTime,

        @JsonProperty("application_context")
        PayPalApplicationContext applicationContext
) {}
