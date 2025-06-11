package com.valuelab.assessment.tracking_number_generator.modal;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Document(collection = "tracking_numbers")
@CompoundIndexes({
        @CompoundIndex(name = "tracking_number_idx", def = "{'trackingNumber': 1}", unique = true)
})
@Data
public class TrackingNumber {
    @Id
    private String id;

    @Field("trackingNumber")
    private String trackingNumber;

    @Field("createdAt")
    private Instant createdAt;

    public TrackingNumber(String trackingNumber, Instant createdAt) {
        this.trackingNumber = trackingNumber;
        this.createdAt = createdAt;
    }
}
