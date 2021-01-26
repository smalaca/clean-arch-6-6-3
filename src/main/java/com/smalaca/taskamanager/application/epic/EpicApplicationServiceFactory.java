package com.smalaca.taskamanager.application.epic;

import com.smalaca.taskamanager.domain.epic.EpicFactory;
import com.smalaca.taskamanager.domain.user.UserDomainRepository;
import com.smalaca.taskamanager.repository.EpicRepository;
import com.smalaca.taskamanager.repository.ProjectRepository;

public class EpicApplicationServiceFactory {
    public EpicApplicationService epicApplicationService(EpicRepository epicRepository, ProjectRepository projectRepository, UserDomainRepository userRepository) {
        return new EpicApplicationService(epicRepository, projectRepository, new EpicFactory(userRepository));
    }
}
