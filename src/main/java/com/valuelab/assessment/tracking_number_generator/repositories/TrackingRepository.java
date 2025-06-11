package com.valuelab.assessment.tracking_number_generator.repositories;

import com.valuelab.assessment.tracking_number_generator.modal.TrackingNumber;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackingRepository extends MongoRepository<TrackingNumber, String> {
    boolean existsByTrackingNumber(String trackingNumber);
}
