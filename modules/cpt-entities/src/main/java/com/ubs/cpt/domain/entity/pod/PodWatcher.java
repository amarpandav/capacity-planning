package com.ubs.cpt.domain.entity.pod;

import com.ubs.cpt.domain.BaseEntity;
import com.ubs.cpt.domain.entity.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Entity representing cpt_pod_watcher table. This way we can identify which user is watching which pod.
 *
 * Pod watchers could be Line Managers, Crew leads, stream leads as they are not directly part of the pod.
 * Pod watchers have no rights to modify pod. Anybody could be Pod watcher!
 *
 */
@Entity
@Table(
        name = PodWatcher.TABLE_NAME
)
public class PodWatcher extends BaseEntity<PodWatcher> {
    public static final String TABLE_NAME = "cpt_pod_watcher";

    public static final class Columns {
        public static final String USER_UUID = "user_uuid";
        public static final String POD_UUID = "pod_uuid";
    }

    //@CascadeDelete
    @OnDelete(action = OnDeleteAction.CASCADE) // when user is deleted, they must also be removed from this pod.
    @ManyToOne
    @JoinColumn(name = Columns.USER_UUID)
    private User user;

    protected PodWatcher() {// required by JPA
    }

    public PodWatcher(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}

