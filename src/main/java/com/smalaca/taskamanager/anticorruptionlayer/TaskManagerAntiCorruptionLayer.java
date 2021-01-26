package com.smalaca.taskamanager.anticorruptionlayer;

import com.smalaca.taskamanager.domain.productowner.ProductOwnerDomainRepository;
import com.smalaca.taskamanager.domain.team.TeamDomainRepository;
import com.smalaca.taskamanager.domain.user.UserDomainRepository;
import com.smalaca.taskamanager.model.entities.ProductOwner;
import com.smalaca.taskamanager.model.entities.Team;
import com.smalaca.taskamanager.model.entities.User;
import com.smalaca.taskamanager.repository.ProductOwnerRepository;
import com.smalaca.taskamanager.repository.TeamRepository;
import com.smalaca.taskamanager.repository.UserRepository;

public class TaskManagerAntiCorruptionLayer implements TeamDomainRepository, UserDomainRepository, ProductOwnerDomainRepository {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final ProductOwnerRepository productOwnerRepository;

    public TaskManagerAntiCorruptionLayer(TeamRepository teamRepository, UserRepository userRepository, ProductOwnerRepository productOwnerRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.productOwnerRepository = productOwnerRepository;
    }

    @Override
    public Long save(Team team) {
        return teamRepository.save(team).getId();
    }

    @Override
    public boolean doesNotExistByName(String name) {
        return teamRepository.findByName(name).isEmpty();
    }

    @Override
    public Long save(User user) {
        return userRepository.save(user).getId();
    }

    @Override
    public boolean doesNotExistsByFirstAndLastName(String firstName, String lastName) {
        return userRepository.findByUserNameFirstNameAndUserNameLastName(firstName, lastName).isEmpty();
    }

    @Override
    public boolean doesNotExistByFirstAndLastName(String firstName, String lastName) {
        return productOwnerRepository.findByFirstNameAndLastName(firstName, lastName).isEmpty();
    }

    @Override
    public Long save(ProductOwner productOwner) {
        return productOwnerRepository.save(productOwner).getId();
    }
}
