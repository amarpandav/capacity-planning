package com.ubs.cpt.service.impl;

import com.ubs.cpt.service.UserUnavailableService;
import com.ubs.cpt.service.dto.UserAvailableCapacityDto;
import com.ubs.cpt.service.dto.UserBookedCapacityDto;
import com.ubs.cpt.service.dto.UserUnavailableDto;
import com.ubs.cpt.service.impl.mapper.AvailabilityMapper;
import com.ubs.cpt.service.impl.mapper.PodMapper;
import com.ubs.cpt.service.impl.mapper.UserMapper;
import com.ubs.cpt.service.repository.UserBookedCapacityRepository;
import com.ubs.cpt.service.repository.UserUnAvailabilityCapacityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserUnavailableServiceImpl implements UserUnavailableService {

    private final UserUnAvailabilityCapacityRepository userUnAvailabilityCapacityRepository;
    private final UserBookedCapacityRepository userBookedCapacityRepository;

    public UserUnavailableServiceImpl(UserUnAvailabilityCapacityRepository userUnAvailabilityCapacityRepository, UserBookedCapacityRepository userBookedCapacityRepository) {
        this.userUnAvailabilityCapacityRepository = userUnAvailabilityCapacityRepository;
        this.userBookedCapacityRepository = userBookedCapacityRepository;
    }

    @Override
    public UserUnavailableDto findUnavailableDates() {
        List<UserAvailableCapacityDto> userAbsences = userUnAvailabilityCapacityRepository.findUserAbsences().stream()
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
