package com.ubs.cpt.service.impl;

import com.ubs.cpt.service.UserUnavailableService;
import com.ubs.cpt.service.dto.UserAvailableCapacityDto;
import com.ubs.cpt.service.dto.UserBookedCapacityDto;
import com.ubs.cpt.service.dto.UserUnavailableDto;
import com.ubs.cpt.service.impl.mapper.AvailabilityMapper;
import com.ubs.cpt.service.impl.mapper.PodMapper;
import com.ubs.cpt.service.impl.mapper.UserMapper;
import com.ubs.cpt.service.repository.UserAvailabilityCapacityRepository;
import com.ubs.cpt.service.repository.UserBookedCapacityRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;

@Service
public class UserUnavailableServiceImpl implements UserUnavailableService {

    private final UserAvailabilityCapacityRepository userAvailabilityCapacityRepository;
    private final UserBookedCapacityRepository userBookedCapacityRepository;

    public UserUnavailableServiceImpl(UserAvailabilityCapacityRepository userAvailabilityCapacityRepository, UserBookedCapacityRepository userBookedCapacityRepository) {
        this.userAvailabilityCapacityRepository = userAvailabilityCapacityRepository;
        this.userBookedCapacityRepository = userBookedCapacityRepository;
    }

    @Override
    public UserUnavailableDto findUnavailableDates() {
        List<UserAvailableCapacityDto> userAbsences = userAvailabilityCapacityRepository.findUserAbsences().stream()
                .map(userAvailability -> new UserAvailableCapacityDto(userAvailability.getEntityId().getUuid(),
                        userAvailability.getDay(),
                        UserMapper.map(userAvailability.getUser()),
                        AvailabilityMapper.map(userAvailability.getMorningAvailability()),
                        AvailabilityMapper.map(userAvailability.getAfternoonAvailability())))
                .toList();
        List<UserBookedCapacityDto> booked = userBookedCapacityRepository.findAll().stream()
                .map(userBookedCapacity -> new UserBookedCapacityDto(
                        userBookedCapacity.getEntityId().getUuid(),
                        userBookedCapacity.getDay(),
                        UserMapper.map(userBookedCapacity.getUser()),
                        PodMapper.map(userBookedCapacity.getMorningPod()),
                        PodMapper.map(userBookedCapacity.getAfternoonPod())
                ))
                .toList();
        return new UserUnavailableDto(userAbsences, booked);
    }
}
