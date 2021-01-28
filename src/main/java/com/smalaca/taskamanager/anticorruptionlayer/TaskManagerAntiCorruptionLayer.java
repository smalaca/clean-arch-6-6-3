package com.smalaca.taskamanager.anticorruptionlayer;

import com.smalaca.taskamanager.domain.epic.EpicDomain;
import com.smalaca.taskamanager.domain.epic.EpicDomainDto;
import com.smalaca.taskamanager.domain.epic.EpicDomainRepository;
import com.smalaca.taskamanager.domain.owner.OwnerDomainDto;
import com.smalaca.taskamanager.domain.productowner.ProductOwnerDomainRepository;
import com.smalaca.taskamanager.domain.project.ProjectDomain;
import com.smalaca.taskamanager.domain.project.ProjectDomainDto;
import com.smalaca.taskamanager.domain.project.ProjectDomainRepository;
import com.smalaca.taskamanager.domain.user.UserDomain;
import com.smalaca.taskamanager.domain.user.UserDomainRepository;
import com.smalaca.taskamanager.infrastructure.persistence.user.JpaUserDomainRepository;
import com.smalaca.taskamanager.model.embedded.EmailAddress;
import com.smalaca.taskamanager.model.embedded.Owner;
import com.smalaca.taskamanager.model.embedded.PhoneNumber;
import com.smalaca.taskamanager.model.entities.Epic;
import com.smalaca.taskamanager.model.entities.ProductOwner;
import com.smalaca.taskamanager.model.entities.Project;
import com.smalaca.taskamanager.model.enums.ToDoItemStatus;
import com.smalaca.taskamanager.repository.EpicRepository;
import com.smalaca.taskamanager.repository.ProductOwnerRepository;
import com.smalaca.taskamanager.repository.ProjectRepository;
import com.smalaca.taskamanager.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class TaskManagerAntiCorruptionLayer implements UserDomainRepository, ProductOwnerDomainRepository, ProjectDomainRepository, EpicDomainRepository {
    private final UserRepository userRepository;
    private final ProductOwnerRepository productOwnerRepository;
    private final ProjectRepository projectRepository;
    private final EpicRepository epicRepository;
    private final JpaUserDomainRepository jpaUserDomainRepository;

    public TaskManagerAntiCorruptionLayer(
            UserRepository userRepository, ProductOwnerRepository productOwnerRepository, ProjectRepository projectRepository,
            EpicRepository epicRepository, JpaUserDomainRepository jpaUserDomainRepository) {
        this.userRepository = userRepository;
        this.productOwnerRepository = productOwnerRepository;
        this.projectRepository = projectRepository;
        this.epicRepository = epicRepository;
        this.jpaUserDomainRepository = jpaUserDomainRepository;
    }

    @Override
    public Long saveUser(UserDomain userDomain) {
        return jpaUserDomainRepository.saveUser(userDomain);
    }

    @Override
    public boolean doesUserNotExistsByFirstAndLastName(String firstName, String lastName) {
        return jpaUserDomainRepository.doesUserNotExistsByFirstAndLastName(firstName, lastName);
    }

    @Override
    public boolean existsUserById(Long id) {
        return jpaUserDomainRepository.existsUserById(id);
    }

    @Override
    public UserDomain findUserById(Long id) {
        return jpaUserDomainRepository.findUserById(id);
    }

    @Override
    public boolean doesProductOwnerNotExistByFirstAndLastName(String firstName, String lastName) {
        return productOwnerRepository.findByFirstNameAndLastName(firstName, lastName).isEmpty();
    }

    @Override
    public Long saveProductOwner(ProductOwner productOwner) {
        return productOwnerRepository.save(productOwner).getId();
    }

    @Override
    public void saveProject(ProjectDomain projectDomain) {
        Project project = findProject(projectDomain.asDto().getId());

        projectRepository.save(project);
    }

    @Override
    public boolean existsProjectById(Long projectId) {
        return projectRepository.existsById(projectId);
    }

    @Override
    public ProjectDomain findProjectById(Long projectId) {
        Project project = findProject(projectId);
        ProjectDomainDto dto = ProjectDomainDto.builder()
                .id(project.getId())
                .build();
        return new ProjectDomain(dto);
    }

    @Override
    public Long saveEpic(EpicDomain epicDomain) {
        EpicDomainDto dto = epicDomain.asDto();
        Epic epic = new Epic();
        epic.assignProject(findProject(dto.getProject().getId()));
        epic.setOwner(asOwner(dto.getOwner()));
        epic.setTitle(dto.getTitle());
        epic.setDescription(dto.getDescription());
        epic.setStatus(ToDoItemStatus.valueOf(dto.getToDoItemStatus()));

        return epicRepository.save(epic).getId();
    }

    private Owner asOwner(OwnerDomainDto dto) {
        Owner owner = new Owner();
        owner.setFirstName(dto.getFirstName());
        owner.setLastName(dto.getLastName());
        owner.setEmailAddress(emailAddress(dto.getEmailAddress()));
        owner.setPhoneNumber(phoneNumber(dto));
        return owner;
    }

    private PhoneNumber phoneNumber(OwnerDomainDto dto) {
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setNumber(dto.getPhoneNumber());
        phoneNumber.setPrefix(dto.getPhonePrefix());
        return phoneNumber;
    }

    private EmailAddress emailAddress(String emailAddress) {
        EmailAddress address = new EmailAddress();
        address.setEmailAddress(emailAddress);
        return address;
    }

    private Project findProject(Long projectId) {
        return projectRepository.findById(projectId).get();
    }
}
