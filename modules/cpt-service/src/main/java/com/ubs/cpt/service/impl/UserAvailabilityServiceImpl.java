package com.ubs.cpt.service.impl;

import com.ubs.cpt.service.UserAvailabilityService;
import com.ubs.cpt.service.dto.UserAvailableCapacityDto;
import com.ubs.cpt.service.impl.mapper.AvailabilityMapper;
import com.ubs.cpt.service.impl.mapper.UserMapper;
import com.ubs.cpt.service.repository.UserUnAvailabilityCapacityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

//TODO: Amar feedback: shouldn't we rename this class to UserUnAvailabilityServiceImpl
//UI need UnAvailability and Availability. So think how you want to do this?
//Availability is calculated virtually. Availability  = what is left over after Booking & UnAvailability
@Service
public class UserAvailabilityServiceImpl implements UserAvailabilityService {

    private final UserUnAvailabilityCapacityRepository repository;

    public UserAvailabilityServiceImpl(UserUnAvailabilityCapacityRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<UserAvailableCapacityDto> getUsersAvailability() {
        return repository.findAll().stream()
                .map(availability -> new UserAvailableCapacityDto(availability.getEntityId().getUuid(),
                        availability.getDay(),
                        UserMapper.map(availability.getUser()),
                        AvailabilityMapper.map(availability.getMorningAvailability()),
                        AvailabilityMapper.map(availability.getAfternoonAvailability())))
                .toList();
    }
}
