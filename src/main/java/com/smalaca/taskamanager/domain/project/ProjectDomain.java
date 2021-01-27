package com.smalaca.taskamanager.domain.project;

public class ProjectDomain {
    private final Long id;

    public ProjectDomain(ProjectDomainDto dto) {
        id = dto.getId();
    }

    public ProjectDomainDto asDto() {
        return ProjectDomainDto.builder()
                .id(id)
                .build();
    }
}
