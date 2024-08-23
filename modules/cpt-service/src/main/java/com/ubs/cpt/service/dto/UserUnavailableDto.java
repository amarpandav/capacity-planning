package com.ubs.cpt.service.dto;

import java.util.List;

public record UserUnavailableDto(
        List<UserAvailableCapacityDto> userAbsentCapacities,
        List<UserBookedCapacityDto> userBookedCapacities
) {
}
