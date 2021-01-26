package com.smalaca.taskamanager.application.epic;

import com.smalaca.taskamanager.domain.epic.EpicDomainRepository;
import com.smalaca.taskamanager.domain.epic.EpicFactory;
import com.smalaca.taskamanager.domain.project.ProjectDomainRepository;
import com.smalaca.taskamanager.dto.EpicDto;
import com.smalaca.taskamanager.exception.ProjectNotFoundException;
import com.smalaca.taskamanager.model.entities.Epic;
import com.smalaca.taskamanager.model.entities.Project;

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
        Project project = findProject(dto);

        Epic epic = epicFactory.create(dto, project);

        projectRepository.saveProject(project);
        return epicRepository.saveEpic(epic);
    }

    private Project findProject(EpicDto dto) {
        if (projectRepository.existsProjectById(dto.getProjectId())) {
            return projectRepository.findProjectById(dto.getProjectId());
        }

        throw new ProjectNotFoundException();
    }
}
