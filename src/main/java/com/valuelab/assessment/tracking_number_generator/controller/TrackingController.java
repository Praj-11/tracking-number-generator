package com.valuelab.assessment.tracking_number_generator.controller;

import com.valuelab.assessment.tracking_number_generator.modal.TrackingRequest;
import com.valuelab.assessment.tracking_number_generator.modal.TrackingResponse;
import com.valuelab.assessment.tracking_number_generator.services.TrackingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tracking-service")
public class TrackingController {

    private final TrackingService service;

    public TrackingController(TrackingService service) {
        this.service = service;
    }

    @GetMapping("/next-tracking-number")
    public ResponseEntity<TrackingResponse> getTrackingNumber(@Valid TrackingRequest request) {
        TrackingResponse response = service.generateUniqueTrackingNumber(request);
        return ResponseEntity.ok(response);
    }
}
