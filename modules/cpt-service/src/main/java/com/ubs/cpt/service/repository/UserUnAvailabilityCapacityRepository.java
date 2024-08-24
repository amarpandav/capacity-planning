package com.ubs.cpt.service.repository;

import com.ubs.cpt.domain.EntityId;
import com.ubs.cpt.domain.entity.user.UserUnAvailableCapacity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserUnAvailabilityCapacityRepository extends JpaRepository<UserUnAvailableCapacity, EntityId<UserUnAvailableCapacity>> {

    @Query("select c from UserUnAvailableCapacity c where c.morningAvailability.availabilityType in ('ABSENT', 'PUBLIC_HOLIDAY')" +
            " or c.afternoonAvailability.availabilityType in ('ABSENT', 'PUBLIC_HOLIDAY') order by c.user.name, c.day ASC")
    List<UserUnAvailableCapacity> findUserAbsences();
}
