package com.smalaca.taskamanager.anticorruptionlayer;

import com.smalaca.taskamanager.domain.epic.EpicDomainRepository;
import com.smalaca.taskamanager.domain.productowner.ProductOwnerDomainRepository;
import com.smalaca.taskamanager.domain.project.ProjectDomainRepository;
import com.smalaca.taskamanager.domain.team.TeamDomainRepository;
import com.smalaca.taskamanager.domain.user.UserDomainRepository;
import com.smalaca.taskamanager.model.entities.Epic;
import com.smalaca.taskamanager.model.entities.ProductOwner;
import com.smalaca.taskamanager.model.entities.Project;
import com.smalaca.taskamanager.model.entities.Team;
import com.smalaca.taskamanager.model.entities.User;
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
    public Long saveUser(User user) {
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
    public Long saveEpic(Epic epic) {
        return epicRepository.save(epic).getId();
    }
}
