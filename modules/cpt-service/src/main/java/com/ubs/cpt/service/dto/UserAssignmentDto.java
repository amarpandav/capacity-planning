package com.ubs.cpt.service.dto;

import com.ubs.cpt.domain.entity.pod.PodMemberRole;
import com.ubs.cpt.domain.entity.user.User;
import com.ubs.cpt.service.impl.mapper.UserMapper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserAssignmentDto {
    private final UserDto user;
    private final PodMemberRole podMemberRole;
    private final Set<AssignmentDto> podAssignments = new HashSet<>();

    public UserAssignmentDto(UserDto user, PodMemberRole podMemberRole) {
        this.user = user;
        this.podMemberRole = podMemberRole;
    }

    public UserAssignmentDto(User user, PodMemberRole podMemberRole, LocalDate startDate, LocalDate endDate) {
        this(UserMapper.map(user), podMemberRole);
        startDate.datesUntil(endDate.plusDays(1))
                .map(day -> {
                    if (day.getDayOfWeek() == DayOfWeek.SATURDAY || day.getDayOfWeek() == DayOfWeek.SUNDAY) {
                        return AssignmentDto.publicHoliday(day);
                    } else {
                        return AssignmentDto.available(day);
                    }
                })
                .forEach(podAssignments::add);
    }

    public UserDto getUser() {
        return user;
    }

    public PodMemberRole getPodMemberRole() {
        return podMemberRole;
    }

    public List<AssignmentDto> getPodAssignments() {
        return podAssignments.stream()
                .sorted(Comparator.comparing(AssignmentDto::day))
                .toList();
    }

    public void add(List<AssignmentDto> toAdd) {
        toAdd.forEach(input -> {
            if (podAssignments.remove(input)) {
                podAssignments.add(input);
            }
        });
    }

}
