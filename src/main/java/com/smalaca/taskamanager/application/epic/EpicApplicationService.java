package com.smalaca.taskamanager.application.epic;

import com.smalaca.taskamanager.domain.epic.EpicBuilder;
import com.smalaca.taskamanager.domain.epic.UserException;
import com.smalaca.taskamanager.dto.EpicDto;
import com.smalaca.taskamanager.exception.ProjectNotFoundException;
import com.smalaca.taskamanager.model.embedded.EmailAddress;
import com.smalaca.taskamanager.model.embedded.Owner;
import com.smalaca.taskamanager.model.embedded.PhoneNumber;
import com.smalaca.taskamanager.model.entities.Epic;
import com.smalaca.taskamanager.model.entities.Project;
import com.smalaca.taskamanager.model.entities.User;
import com.smalaca.taskamanager.repository.EpicRepository;
import com.smalaca.taskamanager.repository.ProjectRepository;
import com.smalaca.taskamanager.repository.UserRepository;

import java.util.Optional;

import static com.smalaca.taskamanager.domain.epic.EpicBuilder.epic;

public class EpicApplicationService {
    private final EpicRepository epicRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public EpicApplicationService(EpicRepository epicRepository, ProjectRepository projectRepository, UserRepository userRepository) {
        this.epicRepository = epicRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public Long create(EpicDto dto) {
        EpicBuilder builder = epic()
                .withTitle(dto.getTitle())
                .withDescription(dto.getDescription())
                .withStatus(dto.getStatus());

        Epic epic = builder.build();

        if (dto.getOwnerId() != null) {
            User user = getUser(dto);
            Owner owner = new Owner();
            owner.setFirstName(user.getUserName().getFirstName());
            owner.setLastName(user.getUserName().getLastName());

            if (user.getEmailAddress() != null) {
                EmailAddress emailAddress = new EmailAddress();
                emailAddress.setEmailAddress(user.getEmailAddress().getEmailAddress());
                owner.setEmailAddress(emailAddress);
            }

            if (user.getPhoneNumber() != null) {
                PhoneNumber phoneNumber = new PhoneNumber();
                phoneNumber.setPrefix(user.getPhoneNumber().getPrefix());
                phoneNumber.setNumber(user.getPhoneNumber().getNumber());
                owner.setPhoneNumber(phoneNumber);
            }

            epic.setOwner(owner);
        }

        Project project = findProject(dto);

        epic.setProject(project);
        project.addEpic(epic);

        projectRepository.save(project);
        Epic saved = epicRepository.save(epic);

        return saved.getId();
    }

    private User getUser(EpicDto dto) {
        Optional<User> found = userRepository.findById(dto.getOwnerId());

        if (found.isEmpty()) {
            throw UserException.notFound(dto.getOwnerId());
        } else {
            return found.get();
        }
    }

    private Project findProject(EpicDto dto) {
        if (!projectRepository.existsById(dto.getProjectId())) {
            throw new ProjectNotFoundException();
        }

        return projectRepository.findById(dto.getProjectId()).get();
    }
}
