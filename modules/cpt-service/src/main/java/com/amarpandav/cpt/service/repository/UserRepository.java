package com.amarpandav.cpt.service.repository;

import com.amarpandav.cpt.domain.EntityId;
import com.amarpandav.cpt.domain.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, EntityId<User>> {
}
