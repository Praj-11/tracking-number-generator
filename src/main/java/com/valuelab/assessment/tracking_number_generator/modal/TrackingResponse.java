package com.valuelab.assessment.tracking_number_generator.modal;

public record TrackingResponse(
        String tracking_number,
        String created_at
) {}
