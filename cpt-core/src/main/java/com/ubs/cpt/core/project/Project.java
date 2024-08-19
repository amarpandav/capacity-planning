package com.ubs.cpt.core.project;

import com.ubs.cpt.core.data.RGB;

public interface Project {
    ProjectId id();
    String description();
    String name();
    String code();
    RGB color();
    ProjectDates projectDates();
}
