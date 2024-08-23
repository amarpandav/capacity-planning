package com.ubs.cpt.service;

import com.ubs.cpt.service.dto.UserAvailableCapacityDto;

import java.util.List;

public interface UserAvailabilityService {

    List<UserAvailableCapacityDto> getUsersAvailability();
}
