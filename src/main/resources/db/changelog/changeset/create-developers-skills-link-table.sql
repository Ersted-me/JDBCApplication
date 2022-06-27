CREATE TABLE developer_skill_link(
    developerId BIGINT NOT NULL,
    skillId BIGINT NOT NULL,
    foreign key (developerId) references developer (id),
    foreign key (skillId) references skill (id)
);