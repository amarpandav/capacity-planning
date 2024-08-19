package com.ubs.cpt.web.data.cpt;

import com.ubs.cpt.core.data.RGB;
import com.ubs.cpt.core.project.Project;
import com.ubs.cpt.core.project.ProjectDates;
import com.ubs.cpt.core.project.ProjectId;
import com.ubs.cpt.web.data.cpt.entity.CptProject;

public class ProjectCptProxy implements Project {
    private final CptProject cptProject;

    public ProjectCptProxy(CptProject cptProject) {
        this.cptProject = cptProject;
    }

    @Override
    public ProjectId id() {
        return new CptProjectId(cptProject.getId());
    }

    @Override
    public String description() {
        return cptProject.getDescription();
    }

    @Override
    public String name() {
        return cptProject.getName();
    }

    @Override
    public String code() {
        return cptProject.getCode();
    }

    @Override
    public RGB color() {
        return RGB.parseHexString(cptProject.getColor());
    }

    @Override
    public ProjectDates projectDates() {
        return new ProjectDates(
            cptProject.getStartDate(),
            cptProject.getEndDate()
        );
    }
}
