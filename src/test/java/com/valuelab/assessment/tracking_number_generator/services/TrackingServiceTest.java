package com.valuelab.assessment.tracking_number_generator.services;

import com.valuelab.assessment.tracking_number_generator.modal.TrackingNumber;
import com.valuelab.assessment.tracking_number_generator.modal.TrackingRequest;
import com.valuelab.assessment.tracking_number_generator.modal.TrackingResponse;
import com.valuelab.assessment.tracking_number_generator.repositories.TrackingRepository;
import com.valuelab.assessment.tracking_number_generator.util.TrackingNumberUtil;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrackingServiceTest {

    @Mock
    private TrackingRepository repository;

    @InjectMocks
    private TrackingService service;



    @Test
    void shouldGenerateTrackingNumberSuccessfully() {
        TrackingRequest request = new TrackingRequest(
                "IN", "US", 1.234,
                "2025-06-08T10:00:00Z",
                UUID.randomUUID(),
                "Test Customer",
                "test-customer"
        );

        String generatedTracking = TrackingNumberUtil.generate(request);

        when(repository.save(any())).thenReturn(new TrackingNumber(generatedTracking, Instant.now()));

        TrackingResponse response = service.generateUniqueTrackingNumber(request);

        assertNotNull(response.tracking_number());
        assertTrue(response.tracking_number().matches("^[A-Z0-9]{1,16}$"));
    }

    @Test
    void shouldThrowExceptionForInvalidRequest() {
        TrackingRequest invalidRequest = new TrackingRequest(
                "", "", -1.0, "", null, "", ""
        );

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            service.generateUniqueTrackingNumber(invalidRequest);
        });

        assertEquals("origin_country_id is required.", ex.getMessage());
    }
}
