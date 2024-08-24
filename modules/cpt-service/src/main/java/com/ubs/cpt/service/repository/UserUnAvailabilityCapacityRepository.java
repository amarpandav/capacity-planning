package com.ubs.cpt.service.repository;

import com.ubs.cpt.domain.EntityId;
import com.ubs.cpt.domain.entity.user.UserUnAvailableCapacity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserUnAvailabilityCapacityRepository extends JpaRepository<UserUnAvailableCapacity, EntityId<UserUnAvailableCapacity>> {

    //TODO: Amar feedback: Why only ABSENT?? You should consider ABSENT and public holiday
    @Query("select c from UserUnAvailableCapacity c where c.morningAvailability.availabilityType = 'ABSENT'" +
            " or c.afternoonAvailability.availabilityType = 'ABSENT' order by c.user.name, c.day ASC")
    public List<UserUnAvailableCapacity> findUserAbsences();
}
