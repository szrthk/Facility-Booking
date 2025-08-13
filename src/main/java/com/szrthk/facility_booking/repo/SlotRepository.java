package com.szrthk.facility_booking.repo;

import com.szrthk.facility_booking.domain.Slot;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.Instant;
import java.util.List;

public interface SlotRepository extends MongoRepository<Slot, String> {
    List<Slot> findByTenantIdAndFacilityIdAndStartBetween(
            String tenantId, String facilityId, Instant from, Instant to);
}
