package com.szrthk.facility_booking.api;

import com.szrthk.facility_booking.Service.FacilityService;
import com.szrthk.facility_booking.api.dto.FacilityCreateRequest;
import com.szrthk.facility_booking.api.dto.FacilityResponse;
import com.szrthk.facility_booking.repo.FacilityRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facilities")
@RequiredArgsConstructor
public class FacilityController {
    private final FacilityService service;
    private final FacilityRepository repo;

    @PostMapping
    public FacilityResponse create(@Valid @RequestBody FacilityCreateRequest req) {
        return service.create(req);
    }

    @GetMapping
    public List<FacilityResponse> list(@RequestParam String tenantId) {
        return repo.findByTenantId(tenantId).stream().map(f ->
                new FacilityResponse(
                        f.getId(), f.getTenantId(), f.getName(),
                        f.getCapacity(), f.getSlotDurationMinutes(),
                        f.getOpenHours(),
                        f.getRules().getMaxPerDay(), f.getRules().getCoolDownMins()
                )
        ).toList();
    }
}
