package com.smalaca.taskamanager.application.epic;

import com.smalaca.taskamanager.domain.epic.EpicFactory;
import com.smalaca.taskamanager.repository.EpicRepository;
import com.smalaca.taskamanager.repository.ProjectRepository;
import com.smalaca.taskamanager.repository.UserRepository;

public class EpicApplicationServiceFactory {
    public EpicApplicationService epicApplicationService(EpicRepository epicRepository, ProjectRepository projectRepository, UserRepository userRepository) {
        return new EpicApplicationService(epicRepository, projectRepository, new EpicFactory(userRepository));
    }
}
