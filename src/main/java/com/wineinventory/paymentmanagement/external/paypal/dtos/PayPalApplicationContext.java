package com.wineinventory.paymentmanagement.external.paypal.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PayPalApplicationContext(
        @JsonProperty("brand_name") String brandName,
        @JsonProperty("locale") String locale,
        @JsonProperty("user_action") String userAction,
        @JsonProperty("return_url") String returnUrl,
        @JsonProperty("cancel_url") String cancelUrl
) {}
