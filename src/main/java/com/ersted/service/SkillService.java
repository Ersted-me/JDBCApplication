package com.ersted.service;

import com.ersted.exception.SkillNotFoundException;
import com.ersted.model.Skill;

public interface SkillService extends BaseService<Skill, Long>{
    Skill getByName(String name) throws SkillNotFoundException;
}
