package com.smalaca.taskamanager.application.epic;

import com.smalaca.taskamanager.domain.epic.EpicDomainRepository;
import com.smalaca.taskamanager.domain.epic.EpicFactory;
import com.smalaca.taskamanager.domain.project.ProjectDomainRepository;
import com.smalaca.taskamanager.domain.user.UserDomainRepository;

public class EpicApplicationServiceFactory {
    public EpicApplicationService epicApplicationService(
            EpicDomainRepository epicRepository, ProjectDomainRepository projectRepository, UserDomainRepository userRepository) {
        return new EpicApplicationService(epicRepository, projectRepository, new EpicFactory(userRepository));
    }
}
