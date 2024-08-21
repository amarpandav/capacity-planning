package com.ubs.cpt.domain.entity.pod;

import com.ubs.cpt.domain.BaseEntity;
import com.ubs.cpt.domain.base.FieldConstants;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing cpt_pod table.
 *
 */
@Entity
@Table(
        name = Pod.TABLE_NAME
)
public class Pod extends BaseEntity<Pod> {
    public static final String TABLE_NAME = "cpt_pod";

    public static final class Columns {
        public static final String POD_NAME = "pod_name";
        public static final String POD_DESCRIPTION = "pod_description";
        public static final String POD_STYLE_CLASS = "pod_style_class";
    }

    @Column(name = Columns.POD_NAME, length = FieldConstants.POD_NAME, nullable = false)
    private String podName;

    @Column(name = Columns.POD_DESCRIPTION, length = FieldConstants.DESCRIPTION_SHORT, nullable = false)
    private String podDescription;

    /**
     * UI has defined ca. 50 pod legend colors. While creating new pod, Pod leads have to select 1 style class out of 50.
     * Pod lead is also free to change the legend color any time using Pod Management UI functionality.
     */
    @Column(name = Columns.POD_STYLE_CLASS, length = FieldConstants.GENERAL_50, nullable = false)
    private String podStyleClass;

    //@CascadeDelete
    @OnDelete(action = OnDeleteAction.CASCADE) // when pod is deleted, pod members must be deleted too.
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = PodMember.class)
    @JoinColumn(name = PodMember.Columns.POD_UUID, nullable = false)
    private Set<PodMember> podMembers;

    //@CascadeDelete
    @OnDelete(action = OnDeleteAction.CASCADE) // when pod is deleted, pod members must be deleted too
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = PodWatcher.class)
    @JoinColumn(name = PodWatcher.Columns.POD_UUID, nullable = false)
    private Set<PodWatcher> podWatchers;

    protected Pod() {// required by JPA
    }

    public Pod(String podName, String podDescription, String podStyleClass) {
        this.podName = podName;
        this.podDescription = podDescription;
        this.podStyleClass = podStyleClass;
        this.podMembers = new HashSet<>();
        this.podWatchers = new HashSet<>();
    }

    public void addPodMembers(PodMember pm) {
        this.podMembers.add(pm);
    }

    public Set<PodMember> getPodMembers() {
        return Collections.unmodifiableSet(podMembers);
    }

    public void addPodWatcher(PodWatcher pw) {
        this.podWatchers.add(pw);
    }

    public Set<PodWatcher> getPodWatchers() {
        return Collections.unmodifiableSet(podWatchers);
    }

    public String getPodName() {
        return podName;
    }

    public String getPodDescription() {
        return podDescription;
    }

    public String getPodStyleClass() {
        return podStyleClass;
    }

}

