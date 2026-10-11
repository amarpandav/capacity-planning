package com.amarpandav.cpt.service.repository;

import com.amarpandav.cpt.domain.EntityId;
import com.amarpandav.cpt.domain.entity.pod.PodMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PodMemberRepository extends JpaRepository<PodMember, EntityId<PodMember>> {

    @Query(value = """
            select pm.*
            from cpt_pod_member pm
            where pm.pod_uuid = :podId
            """, nativeQuery = true)
    List<PodMember> findByPodId(@Param("podId") String podId);
}
