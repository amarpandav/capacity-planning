package com.ubs.cpt.service.repository;

import com.ubs.cpt.domain.EntityId;
import com.ubs.cpt.domain.entity.pod.PodAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface PodAssignmentRepository extends JpaRepository<PodAssignment, EntityId<PodAssignment>> {

    @Query(value = """
            select pa.*
            from cpt_pod_assignment pa
            where pa.day >= :startDate and pa.day <= :endDate and pa.user_uuid in (:userIds)
            order by pa.user_uuid asc, pa.day asc
            """, nativeQuery = true)
    List<PodAssignment> getPodAssignment(@Param("userIds") Set<String> userIds,
                                         @Param("startDate") LocalDate startDate,
                                         @Param("endDate") LocalDate endDate);

    @Query(value = """
            select pa.*
            from cpt_pod_assignment pa
            where pa.day >= :startDate and pa.day <= :endDate and pa.user_uuid in (:userIds)
                and pa.morning_pod_uuid = :podId or pa.afternoon_pod_uuid = :podId
            order by pa.user_uuid asc, pa.day asc
            """, nativeQuery = true)
    List<PodAssignment> getPodAssignment(@Param("userIds") Set<String> userIds,
                                         @Param("podId") String podId,
                                         @Param("startDate") LocalDate startDate,
                                         @Param("endDate") LocalDate endDate);
}
