package com.valuelab.assessment.tracking_number_generator.modal;

import java.util.UUID;

public record TrackingRequest(
        String origin_country_id,
        String destination_country_id,
        double weight,
        String created_at,
        UUID customer_id,
        String customer_name,
        String customer_slug
) {}
