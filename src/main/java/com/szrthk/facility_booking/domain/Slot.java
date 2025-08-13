package com.szrthk.facility_booking.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "slots")
@CompoundIndex(name="tenant_facility_start_unique", def="{ 'tenantId':1, 'facilityId':1, 'start':1 }", unique = true)
public class Slot {
    @Id
    private String id;

    private String tenantId;
    private String facilityId;

    private Instant start;   // stored in UTC
    private Instant end;

    private int maxCapacity;
    @Builder.Default
    private int bookedCount = 0; // will be atomically incremented during booking
}
