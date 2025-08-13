package com.szrthk.facility_booking.api.dto;

public record FacilityResponse(
        String id, String tenantId, String name,
        int capacity, int slotDurationMinutes, String openHours,
        int maxPerDay, int cooldownMins
) {}
