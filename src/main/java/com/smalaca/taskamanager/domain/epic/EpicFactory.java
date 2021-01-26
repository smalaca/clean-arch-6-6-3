package com.smalaca.taskamanager.domain.epic;

import com.smalaca.taskamanager.domain.user.UserDomainRepository;
import com.smalaca.taskamanager.domain.user.UserException;
import com.smalaca.taskamanager.dto.EpicDto;
import com.smalaca.taskamanager.model.embedded.Owner;
import com.smalaca.taskamanager.model.entities.Epic;
import com.smalaca.taskamanager.model.entities.Project;
import com.smalaca.taskamanager.model.entities.User;

import static com.smalaca.taskamanager.domain.epic.EpicBuilder.epic;

public class EpicFactory {
    private final UserDomainRepository userRepository;

    public EpicFactory(UserDomainRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Epic create(EpicDto dto, Project project) {
        EpicBuilder builder = epic()
            .withTitle(dto.getTitle())
            .withDescription(dto.getDescription())
            .withStatus(dto.getStatus());

        if (dto.hasOwnerId()) {
            Owner owner = getUser(dto.getOwnerId()).asOwner();
            builder.withOwner(owner);
        }

        builder.withProject(project);

        return builder.build();
    }

    private User getUser(Long ownerId) {
        if (userRepository.existsUserById(ownerId)) {
            return userRepository.findUserById(ownerId);
        } else {
            throw UserException.notFound(ownerId);
        }
    }
}
