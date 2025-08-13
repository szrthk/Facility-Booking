package com.szrthk.facility_booking.api;


import com.szrthk.facility_booking.Service.SlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/slots")
@RequiredArgsConstructor
public class SlotAdminController {
    private final SlotService slots;

    @PostMapping("/generate")
    public String generate(@RequestParam String tenantId,
                           @RequestParam String facilityId,
                           @RequestParam(defaultValue = "14") int days) {
        int created = slots.generateRollingSlots(tenantId, facilityId, days);
        return "Generated " + created + " slots";
    }
}
