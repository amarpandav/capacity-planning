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

    @Query(value = """
        select p.* 
        from cpt_pod p 
        join cpt_pod_member pm on p.uuid = pm.pod_uuid 
        join cpt_user u on pm.user_uuid = u.uuid 
        where u.uuid in (
            select u1.uuid 
            from cpt_pod p1 
            join cpt_pod_member pm1 on p1.uuid = pm1.pod_uuid 
            join cpt_user u1 on pm1.user_uuid = u1.uuid 
            where p1.uuid = :podId)
    """, nativeQuery = true)
    Set<Pod> findAllVisiblePods(@Param("podId") String podId);

    @Query(value = """
        select * 
        from cpt_pod p 
    """, nativeQuery = true)
    Set<Pod> findAllPods();
}
