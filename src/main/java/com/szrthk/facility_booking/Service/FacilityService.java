package com.szrthk.facility_booking.Service;

import com.szrthk.facility_booking.api.dto.FacilityCreateRequest;
import com.szrthk.facility_booking.api.dto.FacilityResponse;
import com.szrthk.facility_booking.domain.Facility;
import com.szrthk.facility_booking.repo.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FacilityService {
    private final FacilityRepository facilities;

    public FacilityResponse create(FacilityCreateRequest r) {
        var f = Facility.builder()
                .tenantId(r.tenantId())
                .name(r.name())
                .capacity(r.capacity())
                .slotDurationMinutes(r.slotDurationMinutes())
                .openHours(r.openHours())
                .rules(Facility.Rules.builder()
                        .maxPerDay(r.maxPerDay())
                        .coolDownMins(r.cooldownMins())
                        .build())
                .build();

        var saved = facilities.save(f);
        return new FacilityResponse(
                saved.getId(), saved.getTenantId(), saved.getName(),
                saved.getCapacity(), saved.getSlotDurationMinutes(),
                saved.getOpenHours(),
                saved.getRules().getMaxPerDay(), saved.getRules().getCoolDownMins()
        );
    }
}
