package com.smalaca.taskamanager.application.epic;

import com.smalaca.taskamanager.domain.epic.EpicFactory;
import com.smalaca.taskamanager.dto.EpicDto;
import com.smalaca.taskamanager.exception.ProjectNotFoundException;
import com.smalaca.taskamanager.model.entities.Epic;
import com.smalaca.taskamanager.model.entities.Project;
import com.smalaca.taskamanager.repository.EpicRepository;
import com.smalaca.taskamanager.repository.ProjectRepository;
import com.smalaca.taskamanager.repository.UserRepository;

public class EpicApplicationService {
    private final EpicRepository epicRepository;
    private final ProjectRepository projectRepository;
    private final EpicFactory epicFactory;

    public EpicApplicationService(EpicRepository epicRepository, ProjectRepository projectRepository, UserRepository userRepository) {
        this.epicRepository = epicRepository;
        this.projectRepository = projectRepository;
        epicFactory = new EpicFactory(userRepository);
    }

    public Long create(EpicDto dto) {
        Project project = findProject(dto);

        Epic epic = epicFactory.create(dto, project);

        projectRepository.save(project);
        return epicRepository.save(epic).getId();
    }

    private Project findProject(EpicDto dto) {
        if (!projectRepository.existsById(dto.getProjectId())) {
            throw new ProjectNotFoundException();
        }

        return projectRepository.findById(dto.getProjectId()).get();
    }
}
