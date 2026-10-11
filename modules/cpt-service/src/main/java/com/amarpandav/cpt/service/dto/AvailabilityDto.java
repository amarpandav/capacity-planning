package com.amarpandav.cpt.service.dto;

import com.amarpandav.cpt.domain.entity.availability.AvailabilityType;

public record AvailabilityDto(
        String uuid,
        AvailabilityType availabilityType,
        String availabilityDescription,
        String availabilityTypeStyleClass
) {}
