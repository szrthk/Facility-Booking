package com.szrthk.facility_booking.Service;

import com.szrthk.facility_booking.domain.Facility;
import com.szrthk.facility_booking.domain.Slot;
import com.szrthk.facility_booking.repo.FacilityRepository;
import com.szrthk.facility_booking.repo.SlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SlotService {
    private final FacilityRepository facilities;
    private final SlotRepository slots;

    public int generateRollingSlots(String tenantId, String facilityId, int days) {
        Facility f = facilities.findById(facilityId)
                .orElseThrow(() -> new IllegalArgumentException("Facility not found"));

        // parse "08:00-20:00" (24h)
        String[] parts = f.getOpenHours().split("-");
        LocalTime open = LocalTime.parse(parts[0]);
        LocalTime close = LocalTime.parse(parts[1]);

        List<Slot> candidates = new ArrayList<>();
        LocalDate today = LocalDate.now(ZoneOffset.UTC);

        for (int d = 0; d < days; d++) {
            LocalDate date = today.plusDays(d);
            for (LocalTime t = open; t.isBefore(close); t = t.plusMinutes(f.getSlotDurationMinutes())) {
                Instant start = ZonedDateTime.of(date, t, ZoneOffset.UTC).toInstant();
                Instant end = start.plusSeconds(f.getSlotDurationMinutes() * 60L);
                candidates.add(Slot.builder()
                        .tenantId(tenantId)
                        .facilityId(facilityId)
                        .start(start)
                        .end(end)
                        .maxCapacity(f.getCapacity())
                        .bookedCount(0)
                        .build());
            }
        }

        // Prevent duplicates if you re-run generator
        var from = ZonedDateTime.of(today, open, ZoneOffset.UTC).toInstant();
        var to   = ZonedDateTime.of(today.plusDays(days), close, ZoneOffset.UTC).toInstant();

        var existingStarts = slots
                .findByTenantIdAndFacilityIdAndStartBetween(tenantId, facilityId, from, to)
                .stream().map(Slot::getStart).collect(Collectors.toSet());

        var newOnes = candidates.stream()
                .filter(s -> !existingStarts.contains(s.getStart()))
                .toList();

        slots.saveAll(newOnes);
        return newOnes.size();
    }
}
