package com.szrthk.facility_booking.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@Document(collection = "facilities")
@CompoundIndex(name="tenant_name_unique", def="{ 'tenantId': 1, 'name': 1 }", unique = true)
public class Facility {

    @Id
    private String id;

    @NotBlank
    private String tenantId;

    @NotBlank
    private String name;

    @Positive
    private int capacity;

    @Positive
    private int slotDurationMinutes;

    @NotBlank
    private String openHours;

    @Builder.Default
    private Rules rules =  new Rules();
    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Rules {
        @Positive @Builder.Default
        private int maxPerDay = 2;

        @Positive @Builder.Default
        private int coolDownMins = 60;
    }
}
