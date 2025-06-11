package com.valuelab.assessment.tracking_number_generator.controller;

import com.valuelab.assessment.tracking_number_generator.modal.TrackingRequest;
import com.valuelab.assessment.tracking_number_generator.modal.TrackingResponse;
import com.valuelab.assessment.tracking_number_generator.services.TrackingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tracking/api/v1")
public class TrackingController {

    private final TrackingService service;

    public TrackingController(TrackingService service) {
        this.service = service;
    }

    @GetMapping("/next-tracking-number")
    public ResponseEntity<TrackingResponse> getTrackingNumber(TrackingRequest request) {
        TrackingResponse response = service.generateUniqueTrackingNumber(request);
        return ResponseEntity.ok(response);
    }
}
