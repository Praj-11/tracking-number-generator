package com.valuelab.assessment.tracking_number_generator.services;

import com.mongodb.DuplicateKeyException;
import com.valuelab.assessment.tracking_number_generator.modal.TrackingNumber;
import com.valuelab.assessment.tracking_number_generator.modal.TrackingRequest;
import com.valuelab.assessment.tracking_number_generator.modal.TrackingResponse;
import com.valuelab.assessment.tracking_number_generator.repositories.TrackingRepository;
import com.valuelab.assessment.tracking_number_generator.util.TrackingNumberUtil;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

import javax.naming.ServiceUnavailableException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TrackingService {

    private final TrackingRepository repository;

    public TrackingService(TrackingRepository repository) {
        this.repository = repository;
    }

    @Retry(name = "generateTrackingNumber", fallbackMethod = "fallbackTracking")
    public TrackingResponse generateUniqueTrackingNumber(TrackingRequest request) {
        int maxAttempts = 10;
        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            String trackingNumber = TrackingNumberUtil.generate(request);

            try {
                repository.save(new TrackingNumber(trackingNumber, request.created_at()));
                return new TrackingResponse(trackingNumber, ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
            } catch (DuplicateKeyException e) {
                //Retry loop
            }
        }
        throw new IllegalStateException("Failed to generate a unique tracking number after multiple attempts.");
    }

    // Fallback method for retry
    public TrackingResponse fallbackTracking(TrackingRequest request, Exception ex) throws ServiceUnavailableException {
        throw new ServiceUnavailableException("Temporary issue in generating tracking number. Please try again later.");
    }
}