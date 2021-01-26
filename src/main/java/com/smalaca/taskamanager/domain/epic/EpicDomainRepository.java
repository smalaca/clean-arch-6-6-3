package com.smalaca.taskamanager.domain.epic;

import com.smalaca.taskamanager.model.entities.Epic;

public interface EpicDomainRepository {
    Long saveEpic(Epic epic);
}
