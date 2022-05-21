package com.ersted.service.impl;

import com.ersted.exception.AlreadyExistException;
import com.ersted.exception.NotFoundException;
import com.ersted.model.Skill;
import com.ersted.repository.SkillRepository;
import com.ersted.service.SkillService;

import java.util.List;

public class SkillServiceImpl implements SkillService {
    private final SkillRepository repository;

    public SkillServiceImpl(SkillRepository repository) {
        this.repository = repository;
    }

    @Override
    public Skill create(Skill skill){
        return repository.create(skill);
    }

    @Override
    public Skill getById(Long id) throws NotFoundException {
        Skill skill = repository.getById(id);

        if (skill == null) {
            throw new NotFoundException("Skill not found by ID: " + id);
        }

        return repository.getById(id);
    }

    @Override
    public Skill getByName(String name) throws NotFoundException {
        Skill skill = repository.getByName(name);

        if(skill == null)
            throw new NotFoundException(
                    String.format("Skill with name: %s not found.", name));

        return skill;
    }

    @Override
    public Skill update(Skill skill) throws AlreadyExistException {
        Skill updatedSkill = repository.update(skill);

        if(updatedSkill == null)
            throw new AlreadyExistException(
                    String.format("Skill with name: %s already exists", skill.getSkill()));

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
