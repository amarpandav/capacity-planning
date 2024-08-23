package com.ubs.cpt.service.dto;

import com.ubs.cpt.domain.entity.availability.AvailabilityType;

public record AvailabilityDto(
        String uuid,
        AvailabilityType availabilityType,
        String availabilityDescription,
        String availabilityTypeStyleClass
) {}
