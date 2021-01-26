package com.smalaca.taskamanager.application.epic;

import com.smalaca.taskamanager.domain.epic.EpicFactory;
import com.smalaca.taskamanager.domain.project.ProjectDomainRepository;
import com.smalaca.taskamanager.domain.user.UserDomainRepository;
import com.smalaca.taskamanager.repository.EpicRepository;

public class EpicApplicationServiceFactory {
    public EpicApplicationService epicApplicationService(EpicRepository epicRepository, ProjectDomainRepository projectRepository, UserDomainRepository userRepository) {
        return new EpicApplicationService(epicRepository, projectRepository, new EpicFactory(userRepository));
    }
}
