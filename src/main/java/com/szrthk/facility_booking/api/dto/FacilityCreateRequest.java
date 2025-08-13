package com.szrthk.facility_booking.api.dto;

import jakarta.validation.constraints.*;

public record FacilityCreateRequest (
    @NotBlank String tenantId,
    @NotBlank String name,
    @Positive int capacity,
    @Positive int slotDurationMinutes,
    @NotBlank String openHours,   // "08:00-20:00"
    @Positive int maxPerDay,
    @Positive int cooldownMins
){}
