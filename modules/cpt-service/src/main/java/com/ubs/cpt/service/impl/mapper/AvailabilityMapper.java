package com.ubs.cpt.service.impl.mapper;

import com.ubs.cpt.domain.entity.availability.Availability;
import com.ubs.cpt.service.dto.AvailabilityDto;

import static java.util.Objects.isNull;

public class AvailabilityMapper {
    private AvailabilityMapper() {
    }

    public static AvailabilityDto map(Availability availability) {
        if (isNull(availability)) {
            return null;
        }
        return new AvailabilityDto(
                availability.getEntityId().getUuid(),
                availability.getAvailabilityType(),
                availability.getAvailabilityDescription(),
                availability.getAvailabilityTypeStyleClass());
    }
}
