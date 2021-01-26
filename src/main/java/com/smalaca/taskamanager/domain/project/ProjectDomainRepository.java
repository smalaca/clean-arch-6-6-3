package com.smalaca.taskamanager.domain.project;

import com.smalaca.taskamanager.model.entities.Project;

public interface ProjectDomainRepository {
    void saveProject(Project project);

    boolean existsProjectById(Long projectId);

    Project findProjectById(Long projectId);
}
