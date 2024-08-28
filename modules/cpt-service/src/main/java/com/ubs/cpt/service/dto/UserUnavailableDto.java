package com.ubs.cpt.service.dto;

import java.util.List;

//TODO: Amar feedback: why both userAbsentCapacities and userBookedCapacities are part of this class?
public record UserUnavailableDto(
        List<UserAvailableCapacityDto> userAbsentCapacities,
        List<UserBookedCapacityDto> userBookedCapacities
) {
}
