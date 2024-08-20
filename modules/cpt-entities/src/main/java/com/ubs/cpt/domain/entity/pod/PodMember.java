package com.ubs.cpt.domain.entity.pod;

import com.ubs.cpt.domain.BaseEntity;
import com.ubs.cpt.domain.base.FieldConstants;
import com.ubs.cpt.domain.entity.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Entity to define user and pod relationship called Pod member. This way we can identify which user is part of which pod and their role.
 * In a Pod user can play single role officially.
 *
 * Pod Roles would be Developers, BAs, SAs etc and not the Line Managers, Crew leads, stream leads as they are not directly part of the pod, they could be pod watchers.
 */
@Entity
@Table(
        name = PodMember.TABLE_NAME
)
public class PodMember extends BaseEntity<PodMember> {
    public static final String TABLE_NAME = "cpt_pod_member";

    public static final class Columns {
        public static final String USER_UUID = "user_uuid";
        public static final String POD_MEMBER_ROLE = "pod_member_role";
        public static final String POD_UUID = "pod_uuid";
    }

    //@CascadeDelete
    @OnDelete(action = OnDeleteAction.CASCADE) // when user is deleted, they must also be removed from this pod.
    @ManyToOne
    @JoinColumn(name = Columns.USER_UUID)
    private User user;

    @Enumerated(EnumType.STRING)
    @Basic(optional = false)
    @Column(name = Columns.POD_MEMBER_ROLE, length = FieldConstants.ENUM_30, nullable = false)
    private PodMemberRole podMemberRole;

    protected PodMember() {// required by JPA
    }

    public PodMember(User user, PodMemberRole podMemberRole) {
        this.user = user;
        this.podMemberRole = podMemberRole;
    }

    public User getUser() {
        return user;
    }

    public PodMemberRole getPodMemberRole() {
        return podMemberRole;
    }
}

