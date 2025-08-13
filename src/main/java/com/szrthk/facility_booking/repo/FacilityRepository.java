package com.szrthk.facility_booking.repo;

import com.szrthk.facility_booking.domain.Facility;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FacilityRepository extends MongoRepository<Facility, String> {
    List<Facility> findByTenantId(String tenantId);
}
