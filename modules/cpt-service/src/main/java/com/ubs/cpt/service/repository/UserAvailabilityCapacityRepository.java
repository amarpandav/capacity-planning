package com.ubs.cpt.service.repository;

import com.ubs.cpt.domain.EntityId;
import com.ubs.cpt.domain.entity.user.UserAvailableCapacity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserAvailabilityCapacityRepository extends JpaRepository<UserAvailableCapacity, EntityId<UserAvailableCapacity>> {

    @Query("select c from UserAvailableCapacity c where c.morningAvailability.availabilityType = 'ABSENT'" +
            " or c.afternoonAvailability.availabilityType = 'ABSENT' order by c.user.name, c.day ASC")
    public List<UserAvailableCapacity> findUserAbsences();
}
