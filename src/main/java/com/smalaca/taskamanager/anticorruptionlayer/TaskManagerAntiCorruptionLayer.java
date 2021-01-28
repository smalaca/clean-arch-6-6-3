package com.smalaca.taskamanager.anticorruptionlayer;

import com.smalaca.taskamanager.domain.epic.EpicDomain;
import com.smalaca.taskamanager.domain.epic.EpicDomainDto;
import com.smalaca.taskamanager.domain.epic.EpicDomainRepository;
import com.smalaca.taskamanager.domain.owner.OwnerDomainDto;
import com.smalaca.taskamanager.domain.productowner.ProductOwnerDomainRepository;
import com.smalaca.taskamanager.domain.project.ProjectDomain;
import com.smalaca.taskamanager.domain.project.ProjectDomainDto;
import com.smalaca.taskamanager.domain.project.ProjectDomainRepository;
import com.smalaca.taskamanager.domain.team.TeamDomain;
import com.smalaca.taskamanager.domain.team.TeamDomainRepository;
import com.smalaca.taskamanager.domain.user.UserDomain;
import com.smalaca.taskamanager.domain.user.UserDomainDto;
import com.smalaca.taskamanager.domain.user.UserDomainRepository;
import com.smalaca.taskamanager.infrastructure.persistence.team.JpaTeamDomainRepository;
import com.smalaca.taskamanager.model.embedded.EmailAddress;
import com.smalaca.taskamanager.model.embedded.Owner;
import com.smalaca.taskamanager.model.embedded.PhoneNumber;
import com.smalaca.taskamanager.model.embedded.UserName;
import com.smalaca.taskamanager.model.entities.Epic;
import com.smalaca.taskamanager.model.entities.ProductOwner;
import com.smalaca.taskamanager.model.entities.Project;
import com.smalaca.taskamanager.model.entities.User;
import com.smalaca.taskamanager.model.enums.TeamRole;
import com.smalaca.taskamanager.model.enums.ToDoItemStatus;
import com.smalaca.taskamanager.repository.EpicRepository;
import com.smalaca.taskamanager.repository.ProductOwnerRepository;
import com.smalaca.taskamanager.repository.ProjectRepository;
import com.smalaca.taskamanager.repository.TeamRepository;
import com.smalaca.taskamanager.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class TaskManagerAntiCorruptionLayer implements
        TeamDomainRepository, UserDomainRepository, ProductOwnerDomainRepository, ProjectDomainRepository, EpicDomainRepository {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final ProductOwnerRepository productOwnerRepository;
    private final ProjectRepository projectRepository;
    private final EpicRepository epicRepository;
    private final JpaTeamDomainRepository teamDomainRepository;

    public TaskManagerAntiCorruptionLayer(
            TeamRepository teamRepository, UserRepository userRepository, ProductOwnerRepository productOwnerRepository,
            ProjectRepository projectRepository, EpicRepository epicRepository, JpaTeamDomainRepository teamDomainRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.productOwnerRepository = productOwnerRepository;
        this.projectRepository = projectRepository;
        this.epicRepository = epicRepository;
        this.teamDomainRepository = teamDomainRepository;
    }

    @Override
    public Long saveTeam(TeamDomain teamDomain) {
        return teamDomainRepository.saveTeam(teamDomain);
    }

    @Override
    public boolean doesTeamNotExistByName(String name) {
        return teamDomainRepository.doesTeamNotExistByName(name);
    }

    @Override
    public Long saveUser(UserDomain userDomain) {
        UserDomainDto userDomainDto = userDomain.asDto();
        User user = new User();
        user.setLogin(userDomainDto.getLogin());
        user.setPassword(userDomainDto.getPassword());
        user.setTeamRole(TeamRole.valueOf(userDomainDto.getTeamRole()));
        UserName userName = new UserName();
        userName.setFirstName(userDomainDto.getFirstName());
        userName.setLastName(userDomainDto.getLastName());
        user.setUserName(userName);

        return userRepository.save(user).getId();
    }

    @Override
    public boolean doesUserNotExistsByFirstAndLastName(String firstName, String lastName) {
        return userRepository.findByUserNameFirstNameAndUserNameLastName(firstName, lastName).isEmpty();
    }

    @Override
    public boolean existsUserById(Long id) {
        return userRepository.findById(id).isPresent();
    }

    @Override
    public UserDomain findUserById(Long id) {
        User user = userRepository.findById(id).get();
        UserDomainDto dto = UserDomainDto.builder()
                .login(user.getLogin())
                .password(user.getPassword())
                .teamRole(user.getTeamRole().name())
                .firstName(user.getUserName().getFirstName())
                .lastName(user.getUserName().getLastName())
                .emailAddress(user.getEmailAddress().getEmailAddress())
                .phoneNumber(user.getPhoneNumber().getNumber())
                .phonePrefix(user.getPhoneNumber().getPrefix())
                .build();
        return new UserDomain(dto);
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
