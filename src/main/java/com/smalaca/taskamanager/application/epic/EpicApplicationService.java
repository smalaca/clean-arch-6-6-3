package com.smalaca.taskamanager.application.epic;

import com.smalaca.taskamanager.domain.epic.EpicDomain;
import com.smalaca.taskamanager.domain.epic.EpicDomainRepository;
import com.smalaca.taskamanager.domain.epic.EpicFactory;
import com.smalaca.taskamanager.domain.project.ProjectDomain;
import com.smalaca.taskamanager.domain.project.ProjectDomainRepository;
import com.smalaca.taskamanager.dto.EpicDto;
import com.smalaca.taskamanager.exception.ProjectNotFoundException;

public class EpicApplicationService {
    private final EpicDomainRepository epicRepository;
    private final ProjectDomainRepository projectRepository;
    private final EpicFactory epicFactory;

    EpicApplicationService(EpicDomainRepository epicRepository, ProjectDomainRepository projectRepository, EpicFactory epicFactory) {
        this.epicRepository = epicRepository;
        this.projectRepository = projectRepository;
        this.epicFactory = epicFactory;
    }

    public Long create(EpicDto dto) {
        ProjectDomain project = findProject(dto);

        EpicDomain epic = epicFactory.create(dto, project);

        projectRepository.saveProject(project);
        return epicRepository.saveEpic(epic);
    }

    private ProjectDomain findProject(EpicDto dto) {
        if (projectRepository.existsProjectById(dto.getProjectId())) {
            return projectRepository.findProjectById(dto.getProjectId());
        }

        throw new ProjectNotFoundException();
    }
}
