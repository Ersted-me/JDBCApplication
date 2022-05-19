package com.ersted.service;

import com.ersted.exception.SkillAlreadyExistException;
import com.ersted.exception.SkillNotFoundException;
import com.ersted.model.Skill;
import com.ersted.repository.SkillRepository;

import java.util.List;

public class SkillServiceImpl implements SkillService {
    private final SkillRepository repository;

    public SkillServiceImpl(SkillRepository repository) {
        this.repository = repository;
    }

    @Override
    public Skill create(Skill skill) {
        return repository.create(skill);
    }

    @Override
    public Skill getById(Long id) throws SkillNotFoundException {
        Skill skill = repository.getById(id);

        if (skill == null) {
            throw new SkillNotFoundException("Skill not found by ID: " + id);
        }

        return repository.getById(id);
    }

    @Override
    public Skill getByName(String name) throws SkillNotFoundException {
        Skill skill = repository.getByName(name);

        if(skill == null)
            throw new SkillNotFoundException("Skill with name: " + name + " not found.");

        return skill;
    }

    @Override
    public Skill update(Skill skill) throws SkillAlreadyExistException {
        Skill updatedSkill = repository.update(skill);

        if(updatedSkill == null)
            throw new SkillAlreadyExistException("Skill with name: " + skill.getSkill() + "already exists");

        return updatedSkill;
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Skill> getAll() {
        return repository.getAll();
    }
}
