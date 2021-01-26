package com.smalaca.taskamanager.domain.epic;

import com.smalaca.taskamanager.dto.EpicDto;
import com.smalaca.taskamanager.model.entities.Epic;
import com.smalaca.taskamanager.model.entities.Project;
import com.smalaca.taskamanager.model.entities.User;
import com.smalaca.taskamanager.repository.UserRepository;

import java.util.Optional;

import static com.smalaca.taskamanager.domain.epic.EpicBuilder.epic;

public class EpicFactory {
    private final UserRepository userRepository;

    public EpicFactory(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Epic create(EpicDto dto, Project project) {
        EpicBuilder builder = epic()
            .withTitle(dto.getTitle())
            .withDescription(dto.getDescription())
            .withStatus(dto.getStatus());

        if (dto.hasOwnerId()) {
            builder.withOwner(getUser(dto).asOwner());
        }

        builder.withProject(project);

        return builder.build();
    }

    private User getUser(EpicDto dto) {
        Optional<User> found = userRepository.findById(dto.getOwnerId());

        if (found.isEmpty()) {
            throw UserException.notFound(dto.getOwnerId());
        } else {
            return found.get();
        }
    }
}
