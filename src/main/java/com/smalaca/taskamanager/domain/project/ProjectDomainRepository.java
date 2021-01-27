package com.smalaca.taskamanager.domain.project;

public interface ProjectDomainRepository {
    void saveProject(ProjectDomain project);

    boolean existsProjectById(Long projectId);

    ProjectDomain findProjectById(Long projectId);
}
