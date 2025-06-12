package com.valuelab.assessment.tracking_number_generator.modal;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record TrackingRequest(
        @NotNull
        @Size(min = 2, max = 2, message = "origin country id is not valid.")
        String origin_country_id,
        @NotNull
        @Size(min = 2, max = 2, message = "destination country id is not valid.")
        String destination_country_id,
        @NotNull
        @Digits(integer = 10, fraction = 3, message = "weight is not valid.")
        double weight,
        @NotNull
        @Pattern(
                regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d+)?([+-]\\d{2}:\\d{2}|Z)$",
                message = "Invalid RFC 3339 timestamp format"
        )
        String created_at,
        @NotNull(message = "customer id is required")
        UUID customer_id,
        @NotNull(message = "customer name is required")
        String customer_name,
        @NotNull(message = "customer slug is required")
        String customer_slug
) {}
