package com.ubs.cpt.service.repository;

import com.ubs.cpt.domain.EntityId;
import com.ubs.cpt.domain.entity.pod.Pod;
import com.ubs.cpt.domain.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface PodRepository extends JpaRepository<Pod, EntityId<User>> {

    @Query("select p from Pod p join fetch p.podMembers pm join fetch pm.user u where u.entityId = :userId")
    Set<Pod> findPodsWhereUserIsMember(@Param("userId") EntityId<User> userId);

    @Query("select p from Pod p join fetch p.podWatchers pw join fetch pw.user u where u.entityId = :userId")
    Set<Pod> findPodsWhereUserIsWatcher(@Param("userId") EntityId<User> userId);
}
