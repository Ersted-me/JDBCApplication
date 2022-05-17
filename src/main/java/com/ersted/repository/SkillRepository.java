package com.ersted.repository;

import com.ersted.model.Skill;

public interface SkillRepository extends BaseRepository<Skill, Long>{
    Skill getByName(String name);
}
