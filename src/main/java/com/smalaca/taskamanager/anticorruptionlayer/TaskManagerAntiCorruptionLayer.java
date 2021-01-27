package com.smalaca.taskamanager.anticorruptionlayer;

import com.smalaca.taskamanager.domain.epic.EpicDomain;
import com.smalaca.taskamanager.domain.epic.EpicDomainDto;
import com.smalaca.taskamanager.domain.epic.EpicDomainRepository;
import com.smalaca.taskamanager.domain.productowner.ProductOwnerDomainRepository;
import com.smalaca.taskamanager.domain.project.ProjectDomainRepository;
import com.smalaca.taskamanager.domain.team.TeamDomainRepository;
import com.smalaca.taskamanager.domain.user.UserDomain;
import com.smalaca.taskamanager.domain.user.UserDomainDto;
import com.smalaca.taskamanager.domain.user.UserDomainRepository;
import com.smalaca.taskamanager.model.embedded.UserName;
import com.smalaca.taskamanager.model.entities.Epic;
import com.smalaca.taskamanager.model.entities.ProductOwner;
import com.smalaca.taskamanager.model.entities.Project;
import com.smalaca.taskamanager.model.entities.Team;
import com.smalaca.taskamanager.model.entities.User;
import com.smalaca.taskamanager.model.enums.TeamRole;
import com.smalaca.taskamanager.model.enums.ToDoItemStatus;
import com.smalaca.taskamanager.repository.EpicRepository;
import com.smalaca.taskamanager.repository.ProductOwnerRepository;
import com.smalaca.taskamanager.repository.ProjectRepository;
import com.smalaca.taskamanager.repository.TeamRepository;
import com.smalaca.taskamanager.repository.UserRepository;

public class TaskManagerAntiCorruptionLayer implements
        TeamDomainRepository, UserDomainRepository, ProductOwnerDomainRepository, ProjectDomainRepository, EpicDomainRepository {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final ProductOwnerRepository productOwnerRepository;
    private final ProjectRepository projectRepository;
    private final EpicRepository epicRepository;

    public TaskManagerAntiCorruptionLayer(
            TeamRepository teamRepository, UserRepository userRepository, ProductOwnerRepository productOwnerRepository,
            ProjectRepository projectRepository, EpicRepository epicRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.productOwnerRepository = productOwnerRepository;
        this.projectRepository = projectRepository;
        this.epicRepository = epicRepository;
    }

    @Override
    public Long saveTeam(Team team) {
        return teamRepository.save(team).getId();
    }

    @Override
    public boolean doesTeamNotExistByName(String name) {
        return teamRepository.findByName(name).isEmpty();
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
    public User findUserById(Long id) {
        return userRepository.findById(id).get();
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
    public void saveProject(Project project) {
        projectRepository.save(project);
    }

    @Override
    public boolean existsProjectById(Long projectId) {
        return projectRepository.existsById(projectId);
    }

    @Override
    public Project findProjectById(Long projectId) {
        return projectRepository.findById(projectId).get();
    }

    @Override
    public Long saveEpic(EpicDomain epicDomain) {
        EpicDomainDto dto = epicDomain.asDto();
        Epic epic = new Epic();
        epic.assignProject(dto.getProject());
        epic.setOwner(dto.getOwner());
        epic.setTitle(dto.getTitle());
        epic.setDescription(dto.getDescription());
        epic.setStatus(ToDoItemStatus.valueOf(dto.getToDoItemStatus()));

        return epicRepository.save(epic).getId();
    }
}
