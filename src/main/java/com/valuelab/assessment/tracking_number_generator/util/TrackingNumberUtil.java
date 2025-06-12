package com.valuelab.assessment.tracking_number_generator.util;

import com.valuelab.assessment.tracking_number_generator.modal.TrackingRequest;

import java.time.Instant;

public class TrackingNumberUtil {

    public static String generate(TrackingRequest request) {
        String origin = request.origin_country_id();
        String dest = request.destination_country_id();
        String slug = request.customer_slug().replaceAll("-", "").toUpperCase();
        String slugPart = slug.length() > 4 ? slug.substring(0, 4) : slug;
        int weightHash = (int) (request.weight() * 1000) % 10000;
        String timestamp = Long.toString(Long.parseLong(request.created_at()), 36).toUpperCase();

        String raw = origin + dest + slugPart + weightHash + timestamp;
        return raw.replaceAll("[^A-Z0-9]", "").substring(0, Math.min(16, raw.length()));
    }
}
