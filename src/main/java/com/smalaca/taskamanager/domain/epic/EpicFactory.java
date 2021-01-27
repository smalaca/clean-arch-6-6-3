package com.smalaca.taskamanager.domain.epic;

import com.smalaca.taskamanager.domain.owner.OwnerDomain;
import com.smalaca.taskamanager.domain.project.ProjectDomain;
import com.smalaca.taskamanager.domain.user.UserDomain;
import com.smalaca.taskamanager.domain.user.UserDomainRepository;
import com.smalaca.taskamanager.domain.user.UserException;
import com.smalaca.taskamanager.dto.EpicDto;

import static com.smalaca.taskamanager.domain.epic.EpicDomain.Builder.epic;

public class EpicFactory {
    private final UserDomainRepository userRepository;

    public EpicFactory(UserDomainRepository userRepository) {
        this.userRepository = userRepository;
    }

    public EpicDomain create(EpicDto dto, ProjectDomain project) {
        EpicDomain.Builder builder = epic()
            .withTitle(dto.getTitle())
            .withDescription(dto.getDescription())
            .withStatus(dto.getStatus());

        if (dto.hasOwnerId()) {
            OwnerDomain owner = getUser(dto.getOwnerId()).asOwner();
            builder.withOwner(owner);
        }

        builder.withProject(project);

        return builder.build();
    }

    private UserDomain getUser(Long ownerId) {
        if (userRepository.existsUserById(ownerId)) {
            return userRepository.findUserById(ownerId);
        } else {
            throw UserException.notFound(ownerId);
        }
    }
}
