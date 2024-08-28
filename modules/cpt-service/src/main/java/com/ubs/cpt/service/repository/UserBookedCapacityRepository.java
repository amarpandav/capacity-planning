package com.ubs.cpt.service.repository;

import com.ubs.cpt.domain.EntityId;
import com.ubs.cpt.domain.entity.user.UserBookedCapacity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBookedCapacityRepository extends JpaRepository<UserBookedCapacity, EntityId<UserBookedCapacity>> {
}
